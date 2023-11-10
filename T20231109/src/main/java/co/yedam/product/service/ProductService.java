package co.yedam.product.service;

import java.util.List;

public interface ProductService {
	//목록, 단건조회, 등록, 수정, 삭제 : 
	public List<ProductVO> productList();
	public ProductVO getProduct(int prodCode);
	public List<ProductVO> selectReList();
}
