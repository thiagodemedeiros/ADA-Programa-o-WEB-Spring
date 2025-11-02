package com.example.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

public class ProductService {
    private static final String BASE_URL = "https://dummyjson.com/products";
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Product> list(int limit, int skip) throws IOException, InterruptedException {
        String json = HttpClientUtil.get(BASE_URL + "?limit=" + limit + "&skip=" + skip);
        ProductListResponse response = mapper.readValue(json, ProductListResponse.class);
        return response.getProducts();
    }

    public List<Product> search(String q) throws IOException, InterruptedException {
        String json = HttpClientUtil.get(BASE_URL + "/search?q=" + q);
        ProductListResponse response = mapper.readValue(json, ProductListResponse.class);
        return response.getProducts();
    }
}
