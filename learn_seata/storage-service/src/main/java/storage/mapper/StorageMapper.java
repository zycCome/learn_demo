package storage.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import storage.model.Storage;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
@Repository
public interface StorageMapper  extends Mapper<Storage> {

    @Update("update tab_storage set total = total - #{currentUsed} , used = used + #{currentUsed} where product_id = #{productId}")
    int updateUsed(@Param("productId") long productId , @Param("currentUsed") long currentUsed);

}
