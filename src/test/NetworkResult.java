package test;


import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections.MapUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class NetworkResult {
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";

    public static final String RESULT = "result";
    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String ERROR_CODE = "error_code";

    public static Map<String, Object> success(Object value) {
        return ImmutableMap.of(STATUS, SUCCESS, RESULT, value);
    }

    public static Map<String, Object> success() {
        return ImmutableMap.of(STATUS, SUCCESS);
    }

    public static Map<String, Object> successSkipValue(Object value) {
        return success();
    }

    public static Map<String, Object> result(Boolean success, Object result, NetworkError error) {
        if (success != null && success) {
            return Objects.nonNull(result) ? NetworkResult.success(result) : NetworkResult.success();
        } else {
            return Objects.nonNull(result) ? NetworkResult.failedWithCode(error, result) :
                    NetworkResult.failedWithCode(error);
        }
    }

    public static Map<String, Object> failed(String message) {
        return ImmutableMap.of(STATUS, FAILED, MESSAGE, message);
    }

    public static Map<String, Object> failed(String message, Object extra) {
        return ImmutableMap.of(STATUS, FAILED, MESSAGE, message, RESULT, extra);
    }

    public static Map<String, Object> failedWithCode(NetworkError error) {
        return ImmutableMap.of(STATUS, FAILED, MESSAGE, error.getMsg(), ERROR_CODE, error.getCode());
    }

    public static Map<String, Object> failedWithCode(NetworkError error, Object extra) {
        return ImmutableMap.of(STATUS, FAILED, MESSAGE, error.getMsg(), ERROR_CODE, error.getCode(), RESULT, extra);
    }

    public static Map<String, Object> failedWithCode(String message, String errorCode) {
        return ImmutableMap.of(STATUS, FAILED, MESSAGE, message, ERROR_CODE, errorCode);
    }

    public static CompletionStage<Map<String, Object>> failedWithError(NetworkError error) {
        return CompletableFuture.completedFuture(failedWithCode(error));
    }

    public static CompletionStage<Map<String, Object>> succeedWithResult(Map<String, Object> result) {
        if (null == result) {
            return CompletableFuture.completedFuture(success());
        } else {
            return CompletableFuture.completedFuture(success(result));
        }
    }

    public static String getFailedMessage(Map<String, Object> res){
        return isSuccess(res) ? null : MapUtils.getString(res, MESSAGE);
    }

    public static String getFailedCode(Map<String, Object> res) {
        return isSuccess(res) ? null : MapUtils.getString(res, ERROR_CODE);
    }

    public static boolean checkFailedCode(NetworkError error, Map<String, Object> res) {
        return error.getCode().equals(getFailedCode(res));
    }

    public static boolean isSuccess(Map<String, Object> res) {
        return res != null && SUCCESS.equals(res.get(STATUS));
    }

    public static Object getResult(Map<String, Object> res) {
        return res.get(RESULT);
    }

    public static boolean isFailed(Map<String, Object> res) {
        return !isSuccess(res);
    }

    public interface NetworkError {
        String getMsg();
        String getCode();

        default Map<String, Object> failedAsNetworkResult() {
            return NetworkResult.failedWithCode(this);
        }

        default Map<String, Object> failedAsNetworkResult(Object extra) {
            return NetworkResult.failedWithCode(this, extra);
        }

        default CompletionStage<Map<String, Object>> failedResultAsFuture() {
            return CompletableFuture.completedFuture(failedAsNetworkResult());
        }

        default CompletionStage<Map<String, Object>> failedResultAsFuture(Object extra) {
            return CompletableFuture.completedFuture(failedAsNetworkResult(extra));
        }
    }
}
