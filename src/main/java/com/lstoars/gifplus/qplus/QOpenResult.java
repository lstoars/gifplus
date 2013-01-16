/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lstoars.gifplus.qplus;

/**
 *
 * @author nbzhang
 */
public class QOpenResult extends QPlusResult {

	private static final long serialVersionUID = 7291074244293071519L;

	QOpenResult(String json) {
        super(json);
    }

    @Override
    public String toString() {
        return QOpenResult.class.getSimpleName() +"[" + this.map + "]";
    }

    public QOpenInfo getQPlusInfo() {
        if (!isSuccess()) return null;
        QOpenInfo info = new QOpenInfo();
        info.setFace(getValue("info.face"));
        info.setGender(getValue("info.gender"));
        info.setOuth(getValue("info.outh"));
        info.setNick(getValue("info.nick"));
        return info;
    }

    @Override
    public int getResultCode() {
        return getIntValue("ret");
    }
}
