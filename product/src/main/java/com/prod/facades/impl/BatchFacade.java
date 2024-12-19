package com.prod.facades.impl;

import com.common.DTO.ResponseObject;
import com.prod.JPARepositories.batchs.ProductDataRepository;
import com.prod.batch.DTO.ProductData;
import com.prod.facades.IBatchFacade;
import com.prod.models.ENUM.Type_Image;
import com.prod.models.carts.Color;
import com.prod.models.carts.Color_Size_Product;
import com.prod.models.carts.Size;
import com.prod.models.carts.Small_Quantity;
import com.prod.models.details.Category;
import com.prod.models.details.Detail;
import com.prod.models.details.Quantity;
import com.prod.models.details.Season;
import com.prod.models.products.*;
import com.prod.services.carts.IColorService;
import com.prod.services.carts.IColorSizeProductService;
import com.prod.services.carts.ISizeService;
import com.prod.services.carts.ISmallQuantityService;
import com.prod.services.details.ICategoryService;
import com.prod.services.details.IDetailService;
import com.prod.services.details.IQuantityService;
import com.prod.services.details.ISeasonService;
import com.prod.services.products.IImageService;
import com.prod.services.products.ILabelProductService;
import com.prod.services.products.ILabelService;
import com.prod.services.products.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BatchFacade implements IBatchFacade {
    private static final List<String> seasonList = Arrays.asList("Xuan", "Ha", "Thu", "Dong");
    private static final List<String> sizeKiHieu = Arrays.asList("S", "M", "L", "XL");
    private static final List<String> colorChu = Arrays.asList(
            "Đỏ hoa cương", "Cam", "Cam cà rốt", "Nghệ", "Xanh lá", "Xanh rêu", "Xám xanh"
    );
    private static final String code = "0123456789";
    @Autowired
    private IDetailService detailService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IQuantityService quantityService;
    @Autowired
    private ISeasonService seasonService;
    @Autowired
    private IColorService colorService;
    @Autowired
    private ISizeService sizeService;
    @Autowired
    private IColorSizeProductService cspService;
    @Autowired
    private ILabelService labelService;
    @Autowired
    private ILabelProductService labelProductService;
    @Autowired
    private ProductDataRepository dataRepository;
    @Autowired
    private ISmallQuantityService smallQuantityService;

    @Override
    public ResponseObject<String> insertDetail() {
        List<ProductData> datas = dataRepository.findAll();
        List<Product> products = productService.getProducts();
        Map<String, Integer> product = new HashMap<>();
        for (Product prod : products) {
            product.put(
                    prod.getTitle(),
                    prod.getId()
            );
        }
        Map<String, Integer> dataStr = new HashMap<>();
        for (ProductData productData : datas) {
            dataStr.put(
                    productData.getTitle(),
                    productData.getId()
            );
        }
        List<Category> categories = createCategories(datas);
//        List<Overview> overviews =createDetail();
        createSeason();
        createColors();
        createSize();
        createLabel(categories);
        dataStr.keySet().stream()
                .filter(product.keySet()::contains)
                .forEach(e -> processChain(dataStr.get(e), product.get(e)));
        return ResponseObject.<String>builder()
                .isSuccess(true)
                .data(null)
                .message("Da tao them cac chi tiet")
                .build();
    }

    private void processChain(Integer integer, Integer integer1) {
        Random random = new Random();
        Optional<Product> product = productService.getProductById(integer1);
        Optional<ProductData> productData = dataRepository.findById(integer);
        List<Season> seasons = seasonService.getAllSeasons();
        Season season = seasons.get(random.nextInt(seasons.size()));
        if (product.isPresent() && productData.isPresent()) {
            int productId = product.get().getId();
            imageService.createImages(getImages(productData.get().getImages(),
                    product.get().getTitle(), productId));
            Quantity quantity = createQuantity(productData.get());
            List<String> sig = new ArrayList<>();
            //category
            Optional<Category> category = categoryService.getCategoryByCode(productData.get().getCategoryCode());
            createDetail(category.get(), season, quantity, product.get());
            sig.add(category.get().getName());
            sig.add(season.getName());
            sig.add(season.getYear());
            //color size
            Map<List<String>, List<Color_Size_Product>> csp = createSizeColorProduct(sig, productId, quantity, category.get());
            csp.forEach((k, v) -> sig.addAll(k));
            //label
            //update sig product
            Set<String> signature = new LinkedHashSet<>();
            for (String s : sig){
                String[] split = s.split(" ");
                if (split.length == 1) {
                    signature.add(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
                } else {
                    for (String string : split) {
                        signature.add(string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase());
                    }
                }
            }
            product.get().setSignature(String.join(" ", signature).trim());
            product.get().setUpdate_at(LocalDateTime.now());
            productService.createProduct(product.get());
        }
    }

    private List<Image> getImages(String str, String name, int productId) {
        String[] strings = str
                .replace("[", "")
                .replace("]", "")
                .split(",");
        List<Image> images = new ArrayList<>();
        if (strings[0].startsWith("\"") && strings[0].endsWith("\"")) {
            strings[0] = strings[0].substring(1, strings[0].length() - 1); // Loại bỏ ký tự đầu và cuối
        }
        Image firstImage = Image.builder()
                .name(String.valueOf(Type_Image.ANH_NEN))
                .src(strings[0].replace("\\]\\[", ""))
                .type(Type_Image.ANH_NEN)
                .product_id(productId)
                .build();
        images.add(firstImage);
        for (int i = 1; i < strings.length; i++) {
            if (strings[i].startsWith("\"") && strings[i].endsWith("\"")) {
                strings[i] = strings[i].substring(1, strings[i].length() - 1); // Loại bỏ ký tự đầu và cuối
            }
            Image image = Image.builder()
                    .name(String.valueOf(Type_Image.ANH_CHI_TIET) + i)
                    .src(strings[i])
                    .type(Type_Image.ANH_CHI_TIET)
                    .product_id(productId)
                    .build();
            images.add(image);
        }
        return images;
    }

    private List<Category> createCategories(List<ProductData> data) {
        Set<String> category = new HashSet<>();
        for (ProductData productData : data) {
            category.add(productData.getCategoryCode());
        }
        List<Category> res = new ArrayList<>();
        category.forEach(c -> {
            Category category1 = categoryService.createCategory(
                    Category.builder()
                            .code(c.toUpperCase())
                            .name(getCategoryName(c))
                            .is_active(true)
                            .build()
            );
            res.add(category1);
        });
        return res;
    }

    private void createSeason() {
        seasonList.forEach(s -> {
            seasonService.createSeason(
                    Season.builder()
                            .name(s)
                            .year("2024")
                            .build()
            );
        });
    }

    private String getCategoryName(String c) {
        switch (c) {
            case "QAN" -> {
                return "Quần Áo Nam";
            }
            case "WMC" -> {
                return "Quần Áo Nữ";
            }
            case "GNN" -> {
                return "Giày Nam Nữ";
            }
            case "TXN" -> {
                return "Túi Xách Nữ";
            }
            default -> {
                return "";
            }
        }
    }

    private void createColors() {
        colorChu.forEach(c -> {
            colorService.createColor(
                    Color.builder()
                            .code(getColorCode(c))
                            .value(c)
                            .build()
            );
        });
    }

    private String getColorCode(String name) {
        switch (name) {
            case  "Đỏ hoa cương" -> {
                return "F94144";
            }
            case "Cam" -> {
                return "F3722C";
            }
            case "Cam cà rốt" -> {
                return "F8961E";
            }
            case "Nghệ" -> {
                return "F9C74F";
            }
            case "Xanh lá" -> {
                return "90BE6D";
            }
            case "Xanh rêu" -> {
                return "43AA8B";
            }
            case "Xám xanh" -> {
                return "577590";
            }
            default -> {
                return null;
            }
        }
    }

    private void createSize() {
        sizeKiHieu.forEach(s -> {
            sizeService.createSize(
                    Size.builder()
                            .value(s)
                            .build()
            );
        });
    }

    private void createDetail(Category category, Season season, Quantity quantity, Product product) {
        detailService.createDetail(
                Detail.builder()
                        .category_id(category.getId())
                        .product_id(product.getId())
                        .season_id(season.getId())
                        .quantity_id(quantity.getId())
                        .code(category.getCode())
                        .build()
        );
    }

    private Map<List<String>, List<Color_Size_Product>> createSizeColorProduct(List<String> sig, int productId, Quantity quantity, Category category) {
        Random random = new Random();
        Map<List<String>, List<Color_Size_Product>> res = new HashMap<>();
        List<Color_Size_Product> csp = new ArrayList<>();
        List<Color> colors = colorService.getColors();
        List<Size> sizes = sizeService.getAllSizes();
        int sizeCLSPList = random.nextInt(2) + 2; // tu 2 den 5
        List<String> sigBuilder = new ArrayList<>(sig);
        for (int i = 0; i < sizeCLSPList; i++) {
            int colorIndex = random.nextInt(colors.size());
            int sizeIndex = random.nextInt(sizes.size());
            sigBuilder.add(colors.get(colorIndex).getValue());
            sigBuilder.add(sizes.get(sizeIndex).getValue());
            csp.add(cspService.createColorSizeProduct(
                    Color_Size_Product.builder()
                            .color_id(colors.get(colorIndex).getId())
                            .size_id(sizes.get(sizeIndex).getId())
                            .product_id(productId)
                            .build()
            ));
        }
        res.put(sigBuilder, csp);
        createSmallQuantity(csp, quantity);
        sig.addAll(createLabelProduct(category, productId, sig));
        return res;
    }

    private Quantity createQuantity(ProductData productData) {
        return quantityService.createQuantity(
                Quantity.builder()
                        .sold(productData.getQuantitySold())
                        .quantity(productData.getQuantity())
                        .build()
        );
    }

    private void createSmallQuantity(List<Color_Size_Product> csp, Quantity quantity) {
        Random random = new Random();
        int countSold = 0, countQtt = 0;
        if (csp.size() - 1 <= 0) {
            log.error("San pham co size " + csp.size() + " bi loi");
        }
        if (csp.isEmpty()) {
            log.error("Danh sách csp trống hoặc không hợp lệ");
        }

        // Kiểm tra giá trị quantity có hợp lệ hay không
        if (quantity.getSold() < 0 || quantity.getQuantity() < 0) {
            log.error("Giá trị Sold hoặc Quantity không hợp lệ");
        }
        for (int i = 0; i < csp.size() - 1; i++) {
            int sold = 0;
            int qtt = 0;
            if (quantity.getQuantity() > 0) {
                int qttBound = Math.max(1, quantity.getQuantity() / csp.size());
                qtt = random.nextInt(qttBound);
            }
            if (quantity.getSold() > 0) {
                int soldBound = Math.max(1, quantity.getSold() / csp.size());
                sold = random.nextInt(soldBound);
            }
            // Sinh giá trị ngẫu nhiên
            smallQuantityService.create(
                    Small_Quantity.builder()
                            .quantity_id(quantity.getId())
                            .color_size_product_id(csp.get(i).getId())
                            .sold(sold)
                            .quantity(qtt)
                            .build()
            );
            countSold += sold;
            countQtt += qtt;
        }
        smallQuantityService.create(
                Small_Quantity.builder()
                        .quantity_id(quantity.getId())
                        .color_size_product_id(csp.get(csp.size() - 1).getId())
                        .sold(quantity.getSold() - countSold)
                        .quantity(quantity.getQuantity() - countQtt)
                        .build()
        );
    }

    private void createLabel(List<Category> categories) {
        Set<String> code = categories.stream().map(Category::getCode).collect(Collectors.toSet());
        Set<String> labelStr = new HashSet<>();
        code.forEach(c -> {
            String[] name = getCategoryName(c).split(" ");
            Collections.addAll(labelStr, name);
        });
        labelStr.forEach(c -> {
            labelService.createLabel(
                    Label.builder()
                            .name(c)
                            .code(c.toUpperCase())
                            .build()
            );
        });
    }

    private Set<String> createLabelProduct(Category category, int productId, List<String> sig) {
        String[] cateCode = getCategoryName(category.getCode()).split(" ");
        Set<String> sigBuilder = new HashSet<>(sig);
        for (String s : cateCode) {
            Optional<Label> label = labelService.getLabelByName(s);
            if(label.isPresent()) {
                sigBuilder.add(label.get().getName());
                labelProductService.createLabelProduct(
                        Label_Product.builder()
                                .label_id(label.get().getId())
                                .product_id(productId)
                                .build()
                );
            }
        }
        return sigBuilder;
    }
}
