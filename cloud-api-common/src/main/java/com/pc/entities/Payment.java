package com.pc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author pc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {

    private static final long serialVersionUID = 4020256423674922365L;
    /**
     * ID主键
     */
    private Long id;
    /**
     *期刊 连载
     */
    private String serial;

    public Payment(String serial) {
        this.serial = serial;
    }

}
