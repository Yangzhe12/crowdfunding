package xyz.yangzhe.crowd.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.yangzhe.crowd.entity.Admin;
import xyz.yangzhe.crowd.entity.AdminExample;

public interface AdminMapper {
    int countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    List<Admin> selectAdminByKeyword(String keyword);

    void deleteOLdRelationship(Integer adminId);

    void insertNewRelationship(@Param("adminId") Integer adminId, @Param("roleIdList") List<Integer> roleIdList);
}