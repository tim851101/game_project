package webapp.member.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 請求結果類別
 *
 * @param <T> http response數據型態
 */
@Data
@NoArgsConstructor
public class Result<T> {

    private  String message;

    private  Boolean success;

    private T data;

    /**
     * 設定结果為成功
     *
     * @param msg 消息
     */
    public void setResultSuccess(String msg) {
        this.message = msg;
        this.success = true;
        this.data = null;
    }

    /**
     * 設定结果為成功
     *
     * @param msg  消息
     * @param data http response數據
     */
    public void setResultSuccess(String msg, T data) {
        this.message = msg;
        this.success = true;
        this.data = data;
    }

    /**
     * 設定结果為失败
     *
     * @param msg 消息
     */
    public void setResultFailed(String msg) {
        this.message = msg;
        this.success = false;
        this.data = null;
    }

}
