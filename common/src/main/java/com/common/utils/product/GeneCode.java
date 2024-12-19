package com.common.utils.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@AllArgsConstructor
public class GeneCode {
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String getCategory() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        sb.append("cate-");
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(alphabet.length()));
        }
        return sb.toString();
    }

    public static String getLabel() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        sb.append("label-");
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(alphabet.length()));
        }
        return sb.toString();
    }

    public static String getProduct(int id) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        sb.append("product-").append(id);
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(alphabet.length()));
        }
        return sb.toString();
    }

    public static String getCart(int id){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        sb.append("cart-").append(id);
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(alphabet.length()));
        }
        return sb.toString();
    }
}
