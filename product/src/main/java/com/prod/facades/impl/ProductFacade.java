package com.prod.facades.impl;


import com.common.DTO.ResponseObject;
import com.prod.chains.Chain;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.chains.createProduct.*;
import com.prod.chains.getProducts.*;
import com.prod.facades.DTO.ProductDTO;
import com.prod.facades.IProductFacade;
import com.prod.models.details.Detail;
import com.prod.models.products.*;
import com.prod.services.details.*;
import com.prod.services.products.*;
import com.prod.services.carts.*;
import com.prod.utils.ConvertListPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
@Slf4j
public class ProductFacade implements IProductFacade {
    @Autowired
    private ILabelService labelService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ILabelProductService labelProductService;
    @Autowired
    private ISaleProductService saleProductService;
    @Autowired
    private ISaleService saleService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IOverviewService overviewService;
    @Autowired
    private IReviewService reviewService;
    @Autowired
    private ISeasonService seasonService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IColorService colorService;
    @Autowired
    private ISizeService sizeService;
    @Autowired
    private ISmallQuantityService smallQuantityService;
    @Autowired
    private IColorSizeProductService cspService;
    @Autowired
    private IDetailService detailService;
    @Autowired
    private IQuantityService quantityService;
    @Autowired
    private ConvertListPage<ProductDTO> convertListPage;


    @Override
    public ResponseObject<ProductDTO> createProduct(ProductDTO product) {
        try {
            ChainDataDTO<ProductDTO> dataDTO = ChainDataDTO.<ProductDTO>builder()
                    .value(product)
                    .build();
            Chain<ProductDTO> chain = new Chain<ProductDTO>()
                    .add(new GetSeasonById(seasonService))
                    .add(new GetCategoryById(categoryService))
                    .add(CreateProduct.builder()
                            .productService(productService)
                            .detailService(detailService)
                            .quantityService(quantityService)
                            .build())
                    .add(GetLabelByName.builder()
                            .labelService(labelService)
                            .labelProductService(labelProductService)
                            .build())
                    .add(new CreateImages(imageService))
                    .add(CreateCSPs.builder()
                            .colorService(colorService)
                            .sizeService(sizeService)
                            .smallQuantityService(smallQuantityService)
                            .colorSizeProductService(cspService)
                            .quantityService(quantityService)
                            .detailService(detailService)
                            .productService(productService)
                            .build())
                    .add(CreateSignature.builder()
                            .productService(productService)
                            .build());
            chain.execute(dataDTO);
            if (dataDTO.isSuccess()) {
                return ResponseObject.<ProductDTO>builder()
                        .data(product)
                        .isSuccess(true)
                        .message("Tao san pham thanh cong")
                        .build();
            } else return ResponseObject.<ProductDTO>builder()
                    .message("Khong the tao moi san pham")
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseObject.<ProductDTO>builder()
                    .message("Gap loi server")
                    .build();
        }
    }


    @Override
    public ResponseObject<ProductDTO> updateProduct(ProductDTO productDTO) {
        try {
            ChainDataDTO<ProductDTO> dataDTO = ChainDataDTO.<ProductDTO>builder()
                    .value(productDTO)
                    .build();
            Chain<ProductDTO> chain = new Chain<ProductDTO>()
                    .add(new GetSeasonById(seasonService))
                    .add(new GetCategoryById(categoryService))
                    .add(UpdateProduct.builder()
                            .productService(productService)
                            .detailService(detailService)
                            .quantityService(quantityService)
                            .build())
                    .add(GetLabelByName.builder()
                            .labelService(labelService)
                            .labelProductService(labelProductService)
                            .build())
                    .add(new CreateImages(imageService))
                    .add(CreateCSPs.builder()
                            .colorService(colorService)
                            .sizeService(sizeService)
                            .smallQuantityService(smallQuantityService)
                            .colorSizeProductService(cspService)
                            .detailService(detailService)
                            .quantityService(quantityService)
                            .productService(productService)
                            .build())
                    .add(CreateSignature.builder()
                            .productService(productService)
                            .build());
            chain.execute(dataDTO);
            if (dataDTO.isSuccess()) {
                return ResponseObject.<ProductDTO>builder()
                        .data(productDTO)
                        .isSuccess(true)
                        .message("Cap nhat san pham thanh cong")
                        .build();
            } else {
                log.error(dataDTO.getMessage());
                return ResponseObject.<ProductDTO>builder()
                        .message("Khong the tao moi san pham")
                        .build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseObject.<ProductDTO>builder()
                    .message("Gap loi server")
                    .build();
        }
    }


    @Override
    public ResponseObject<Page<ProductDTO>> getProducts(int page, int size, int category, int season, List<Integer> label, String sortField, String sortDirection) {
        Page<Detail> details = detailService.getPageDetailsByPageWithMultiCondition
                (page, size, category, season, label, sortDirection);
        long total = details.getTotalElements();
        List<Product> products = new ArrayList<>();
        for (Detail detail : details) {
            productService.getProductById(detail.getProduct_id()).ifPresent(products::add);
        }
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            ChainDataDTO<ProductDTO> chainDataDTO = getProductDTO(product);
            productDTOS.add(chainDataDTO.getValue());
        }
        if (productDTOS.isEmpty())
            return emptyProduct();
        else {
            sort(sortField, sortDirection, productDTOS);
            Page<ProductDTO> res = new PageImpl<>(productDTOS, PageRequest.of(page, size), total);
            return successP(res);
        }
    }


    private void sort(String sortField, String sortDirection, List<ProductDTO> productDTOS) {
        Comparator<ProductDTO> comparator;


        switch (sortField) {
            case "price" -> comparator = Comparator.comparing(ProductDTO::getPrice);
            case "title" -> comparator = Comparator.comparing(ProductDTO::getTitle);
            case "score" -> comparator = Comparator.comparing(ProductDTO::getScore);
            case "id" -> comparator = Comparator.comparing(ProductDTO::getProductId);
            default -> throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }


        if ("desc".equalsIgnoreCase(sortDirection)) {
            comparator = comparator.reversed();
        }


        productDTOS.sort(comparator);
    }


    @Override
    public ResponseObject<Page<ProductDTO>> getProductsByLabelId(int id, int page, int size, String sortField, String sortDirect) {
        List<Label_Product> list = labelProductService.getPageLabelProductsByLabelId(id, page, size, "id", sortDirect).getContent();
        Optional<Label> _label = labelService.getLabelById(id);
        if (!list.isEmpty() && _label.isPresent()) {
            List<ProductDTO> products = new ArrayList<>();
            list.forEach(product -> {
                products.add(ProductDTO.builder()
                        .productId(product.getProduct_id())
                        .label(List.of(_label.get().getName()))
                        .build());
            });
            List<ProductDTO> res = new ArrayList<>();
            for (ProductDTO productDTO : products) {
                ChainDataDTO<ProductDTO> chainDataDTO = ChainDataDTO.<ProductDTO>builder()
                        .value(productDTO)
                        .build();
                Chain<ProductDTO> chain = getProductDTOByDetail();
                chain.execute(chainDataDTO);
                res.add(chainDataDTO.getValue());
            }
            if (res.isEmpty()) {
                return emptyProduct();
            } else {
                Page<ProductDTO> done = convertListPage.listToPage(res, page, size);
                return successP(done);
            }
        } else return serverError();
    }


    @Override
    public ResponseObject<Page<ProductDTO>> getProductsBySeasonId(int id, int page, int size, String sortField, String sortDirect) {
        List<Detail> details = detailService.getPageDetailsBySeasonId(id, page, size, "id", sortDirect).getContent();
        if (!details.isEmpty()) {
            List<ProductDTO> products = new ArrayList<>();
            details.forEach(detail -> {
                products.add(ProductDTO.builder()
                        .season_id(id)
                        .productId(detail.getProduct_id())
                        .category(detail.getCategory_id())
                        .build());
            });
            List<ProductDTO> res = new ArrayList<>();
            for (ProductDTO productDTO : products) {
                ChainDataDTO<ProductDTO> chainDataDTO = ChainDataDTO.<ProductDTO>builder()
                        .value(productDTO)
                        .build();
                Chain<ProductDTO> chain = getProductDTOByDetail();
                chain.add(GetLabelByProdId.builder()
                        .labelService(labelService)
                        .labelProductService(labelProductService)
                        .build());
                chain.execute(chainDataDTO);
                res.add(chainDataDTO.getValue());
            }
            if (res.isEmpty()) {
                return emptyProduct();
            } else {
                Page<ProductDTO> done = convertListPage.listToPage(res, page, size);
                return successP(done);
            }
        } else return serverError();
    }


    @Override
    public ResponseObject<ProductDTO> getProductById(int id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            ChainDataDTO<ProductDTO> chainDataDTO = getProductDTO(product.get());
            if (chainDataDTO.isSuccess()) {
                return ResponseObject.<ProductDTO>builder()
                        .message("Tim san pham thanh cong")
                        .isSuccess(true)
                        .data(chainDataDTO.getValue())
                        .build();
            } else {
                log.error(chainDataDTO.getMessage());
                return ResponseObject.<ProductDTO>builder()
                        .message("Khong the lay thong tin san pham")
                        .build();
            }
        } else
            return ResponseObject.<ProductDTO>builder()
                    .message("Gap loi khi tim san pham")
                    .build();
    }


    @Override
    public ResponseObject<Page<ProductDTO>> findProduct(String key, int category, int season, List<Integer> labels, int page, int size, String sortField, String sortDirection) {
        List<Product> products;
        if (Objects.equals(key, "null")) products = productService.getProductsByPage(page, size, sortField, sortDirection).getContent();
        else {
            List<String> keys = new ArrayList<>();
            if (category != 0) {
                keys.addAll(List.of(categoryService.getCategoryById(category).get().getName().split(" ")));
            }
            if (season != 0) {
                keys.addAll(List.of(seasonService.getSeasonById(season).get().getName().split(" ")));
            }
            if (!labels.isEmpty()) {
                for (Integer label : labels) {
                    keys.add(labelService.getLabelById(label).get().getName());
                }
            }
            keys.addAll(List.of(key.split(" ")));
            Set<String> setKey = new LinkedHashSet<>();
            for (String key1 : keys) {
                setKey.add(key1.substring(0, 1).toUpperCase() + key1.substring(1).toLowerCase());
            }
            products = productService.findProducts(setKey, page, size, sortField, sortDirection).getContent();
        }
        List<ProductDTO> res = new ArrayList<>();
        for (Product product : products) {
            ChainDataDTO<ProductDTO> chainDataDTO = getProductDTO(product);
            res.add(chainDataDTO.getValue());
        }
        if (res.isEmpty()) {
            return emptyProduct();
        } else {
            sort(sortField, sortDirection, res);
            Page<ProductDTO> resDTO = new PageImpl<>(res, PageRequest.of(page, size), products.size());
            return successP(resDTO);
        }
    }


    @Override
    public ResponseObject<Page<ProductDTO>> getProductsByCategoryId(int id, int page, int size, String sortField, String sortDirect) {
//        List<Detail> details = detailService.getDetailsByCategoryId(id);
        List<Detail> details = detailService.getPageDetailsByCategoryId(id, page, size, "id", sortDirect).getContent();
        if (!details.isEmpty()) {
            List<ProductDTO> products = new ArrayList<>();
            details.forEach(detail -> {
                products.add(ProductDTO.builder()
                        .season_id(detail.getSeason_id())
                        .productId(detail.getProduct_id())
                        .category(id)
                        .build());
            });
            List<ProductDTO> res = new ArrayList<>();
            for (ProductDTO productDTO : products) {
                ChainDataDTO<ProductDTO> chainDataDTO = ChainDataDTO.<ProductDTO>builder()
                        .value(productDTO)
                        .build();
                Chain<ProductDTO> chain = getProductDTOByDetail();
                chain.add(GetLabelByProdId.builder()
                                .labelService(labelService)
                                .labelProductService(labelProductService)
                                .build())
                        .add(new GetSeasonById(seasonService));
                chain.execute(chainDataDTO);
                res.add(chainDataDTO.getValue());
            }
            if (res.isEmpty()) {
                return emptyProduct();
            } else {
                Page<ProductDTO> done = convertListPage.listToPage(res, page, size);
                return successP(done);
            }
        } else return serverError();
    }


    private ChainDataDTO<ProductDTO> getProductDTO(Product product) {
        ChainDataDTO<ProductDTO> chainDataDTO = setUpProduct(product);
        Chain<ProductDTO> chain = new Chain<ProductDTO>()
                .add(GetColorSizeQuantityByProdId.builder()
                        .colorService(colorService)
                        .sizeService(sizeService)
                        .cspService(cspService)
                        .smallQuantityService(smallQuantityService)
                        .build())
                .add(GetDetailByProdId.builder()
                        .detailService(detailService)
                        .quantityService(quantityService)
                        .build())
                .add(GetLabelByProdId.builder()
                        .labelProductService(labelProductService)
                        .labelService(labelService)
                        .build())
                .add(
                        new GetImageByProdId(imageService)
                )
                .add(GetCateAndSeasonById.builder()
                        .seasonService(seasonService)
                        .categoryService(categoryService)
                        .build())
                .add(CreateSignature.builder()
                        .productService(productService)
                        .build());
        chain.execute(chainDataDTO);
        if (!chainDataDTO.isSuccess()) log.error(chainDataDTO.getMessage() + "/n" + chainDataDTO.getValue());
        return chainDataDTO;
    }


    private Chain<ProductDTO> getProductDTOByDetail() {
        return new Chain<ProductDTO>()
                .add(new GetProdById(productService))
                .add(GetColorSizeQuantityByProdId.builder()
                        .colorService(colorService)
                        .sizeService(sizeService)
                        .cspService(cspService)
                        .smallQuantityService(smallQuantityService)
                        .build())
                .add(new GetImageByProdId(imageService))
                .add(GetDetailByProdId.builder()
                        .detailService(detailService)
                        .quantityService(quantityService)
                        .build())
                .add(GetCateAndSeasonById.builder()
                        .categoryService(categoryService)
                        .seasonService(seasonService)
                        .build())
                .add(GetLabelByProdId.builder()
                        .labelProductService(labelProductService)
                        .labelService(labelService)
                        .build());
    }


    private ChainDataDTO<ProductDTO> setUpProduct(Product product) {
        return ChainDataDTO.<ProductDTO>builder()
                .value(ProductDTO.builder()
                        .productId(product.getId())
                        .title(product.getTitle())
                        .price(product.getPrice())
                        .score(product.getScore())
                        .reviews(product.getReview())
                        .description(product.getDescription())
                        .build())
                .build();
    }


    private ResponseObject<Page<ProductDTO>> successP(Page<ProductDTO> res) {
        return ResponseObject.<Page<ProductDTO>>builder()
                .data(res)
                .isSuccess(true)
                .message("Lay danh sach san pham thanh cong")
                .build();
    }


    private ResponseObject<Page<ProductDTO>> emptyProduct() {
        return ResponseObject.<Page<ProductDTO>>builder()
                .message("Khong the lay danh sach san pham")
                .build();
    }


    private ResponseObject<Page<ProductDTO>> serverError() {
        return ResponseObject.<Page<ProductDTO>>builder()
                .message("Khong tim duoc nhan san pham")
                .build();
    }
}

