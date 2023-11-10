package co.yedam.product.serviceImpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.yedam.common.DataSourceMybatis;
import co.yedam.product.mapper.ProductMapper;
import co.yedam.product.service.ProductService;
import co.yedam.product.service.ProductVO;

public class ProductServiceImpl implements ProductService {
	
	
	SqlSession sqlSession = DataSourceMybatis.getInstance().openSession(true); //true -> 자동커밋

	ProductMapper mapper = sqlSession.getMapper(ProductMapper.class);

	@Override
	public List<ProductVO> productList() {
		return mapper.selectList();
	}

	@Override
	public ProductVO getProduct(int prodCode) {
		return mapper.select(prodCode);
	}

	@Override
	public List<ProductVO> selectReList() {
		return mapper.selectReList();
	}

	


}
