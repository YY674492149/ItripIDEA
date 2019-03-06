package cn.itrip.dao.itripHotelFeature;
import cn.itrip.pojo.ItripHotelFeature;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripHotelFeatureMapper {


	public List<ItripSearchDetailsHotelVO>	getItripHotelDatils(@Param(value = "id") int id)throws Exception;

	public ItripHotelFeature getItripHotelFeatureById(@Param(value = "id") String id)throws Exception;

	public List<ItripHotelFeature>	getItripHotelFeatureListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripHotelFeatureCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripHotelFeature(ItripHotelFeature itripHotelFeature)throws Exception;

	public Integer updateItripHotelFeature(ItripHotelFeature itripHotelFeature)throws Exception;

	public Integer deleteItripHotelFeatureById(@Param(value = "id") Long id)throws Exception;

}
