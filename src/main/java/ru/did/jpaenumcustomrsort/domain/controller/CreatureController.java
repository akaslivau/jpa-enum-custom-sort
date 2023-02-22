package ru.did.neurobet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-01-31T18:08:23.909487400+03:00[Europe/Moscow]")
@Controller
@RequestMapping("${openapi.neurobet.base-path:}")
public class CategoriesApiController implements CategoriesApi {

    private final CategoriesApiDelegate delegate;

    public CategoriesApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) CategoriesApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new CategoriesApiDelegate() {});
    }

    @Override
    public CategoriesApiDelegate getDelegate() {
        return delegate;
    }

}
