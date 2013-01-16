/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lstoars.gifplus.qplus;

/**
 *
 * @author nbzhang
 */
public class QPushResult extends QPlusResult {

	private static final long serialVersionUID = 675930320548761725L;

	QPushResult(String json) {
        super(json);
    }


    @Override
    public String toString() {
        return QPushResult.class.getSimpleName() + "[" + this.map + "]";
    }

    @Override
    public int getResultCode() {
        return getIntValue("ERRCODE");
    }
}
