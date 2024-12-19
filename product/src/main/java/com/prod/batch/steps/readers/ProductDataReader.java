package com.prod.batch.steps.readers;

import com.prod.JPARepositories.batchs.ProductDataRepository;
import com.prod.batch.DTO.ProductData;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductDataReader {
    @Autowired
    private ProductDataRepository productDataRepository;
    public RepositoryItemReader<ProductData> read() {
        RepositoryItemReader<ProductData> reader = new RepositoryItemReader<>();
        reader.setRepository(productDataRepository);
        reader.setMethodName("findAll");
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        reader.setSort(sorts);
        return reader;
    }
}
