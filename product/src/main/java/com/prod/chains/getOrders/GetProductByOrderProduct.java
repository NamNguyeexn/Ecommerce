package com.prod.chains.getOrders;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.OrderDTO;
import com.prod.facades.DTO.OrderProductDTO;
import com.prod.models.ENUM.Type_Image;
import com.prod.models.carts.Cart_Product;
import com.prod.models.carts.Color;
import com.prod.models.carts.Color_Size_Product;
import com.prod.models.carts.Size;
import com.prod.models.products.Image;
import com.prod.models.products.Product;
import com.prod.services.carts.ICartProductService;
import com.prod.services.carts.IColorService;
import com.prod.services.carts.IColorSizeProductService;
import com.prod.services.carts.ISizeService;
import com.prod.services.products.IImageService;
import com.prod.services.products.IProductService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Builder
public class GetProductByOrderProduct implements ChainHandler<OrderDTO> {
    private final IProductService productService;
    private final IColorService colorService;
    private final IColorSizeProductService cspService;
    private final ISizeService sizeService;
    private final IImageService imageService;
    @Override
    public Chain<OrderDTO> handle(ChainDataDTO<OrderDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            OrderDTO dto = chainDataDTO.getValue();
            List<OrderProductDTO> products = new ArrayList<>();
            for (OrderProductDTO opDTO : dto.getProducts()) {
                Optional<Color_Size_Product> csp = cspService.getColorSizeProductById(opDTO.getCsp_id());
                Optional<Product> product = productService.getProductById(csp.get().getProduct_id());
                Optional<Color> color = colorService.getColorById(csp.get().getColor_id());
                Optional<Size> size = sizeService.getSizeById(csp.get().getSize_id());
                Optional<Image> image = imageService.getImageByProdIdAndType(product.get().getId(), Type_Image.ANH_NEN);
                OrderProductDTO dto1 = updateOrderProduct(opDTO, opDTO.getQuantity(), product.get(), color.get(), size.get(), image.get());
                products.add(dto1);
            }
            if (products.isEmpty()) {
                chainDataDTO.setMessage("Gap loi khi tim danh sach san pham").setSuccess(false);
            } else {
                dto.setProducts(products);
                chainDataDTO.setValue(dto).setSuccess(true);
            }
        }
        return new Chain<>(this);
    }

    private OrderProductDTO updateOrderProduct(OrderProductDTO dto,
                                               int quantity,
                                               Product product,
                                               Color color,
                                               Size size,
                                               Image image) {
        dto.setProduct_id(product.getId());
        dto.setName(product.getTitle());
        dto.setPrice(product.getPrice());
        dto.setColor(color.getValue());
        dto.setSize(size.getValue());
        dto.setQuantity(quantity);
        dto.setImage(image.getSrc());
        return dto;
    }
}
