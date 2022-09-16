package by.kharchenko.restcafe.controller;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.product.ProductDTO;
import by.kharchenko.restcafe.model.entity.Product;
import by.kharchenko.restcafe.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.List;

@RestController
public class CommonController {

    private final ProductService productService;

    public CommonController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/home")
    public List<ProductDTO> home() throws ServletException {
        try {
            return productService.findAll();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
