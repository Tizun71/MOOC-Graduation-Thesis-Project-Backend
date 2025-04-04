package vn.tizun.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CategoryPageResponse extends PageResponseAbstract implements Serializable {
    private List<CategoryResponse> categories;
}
