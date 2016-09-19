package org.mm.hf.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface HouseMapper {
	
	public Map<String, Object> selectByAutoID(@Param("iautoid")int autoid);
}
