package storage.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jianjun.ren
 * @since 2021/02/16
 */
@Table(name = "tab_storage")
@Data
@Accessors(chain = true)
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long total;

    private Long productId;

    private Long used;

}
