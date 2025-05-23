package storage.mapper;

import com.baomidou.mybatisplus.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import storage.model.Storage;


/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
@Repository
public interface StorageMapper extends Mapper<Storage> {

    @Update("update tab_storage set total = total - #{currentUsed} , used = used + #{currentUsed} where product_id = #{productId}")
    int updateUsed(@Param("productId") long productId, @Param("currentUsed") long currentUsed);

    @Select("select * from tab_storage where product_id = #{productId} for update")
    Storage getByIdInLock(@Param("productId") long productId);


}
