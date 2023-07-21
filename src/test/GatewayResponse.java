package test;

//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import im.imo.services.bigo_gateway.api_gateway.common.GatewayError;
//import im.imo.services.http_server_core.core.HttpContext;
//import im.imo.services.util.NetworkResult;
//import io.netty.handler.codec.http.HttpResponseStatus;
//
//import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.CompletionException;

/**
 * bigo gateway response
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class GatewayResponse {

//    @JsonProperty("status")
    private String status;

//    @JsonProperty("response")
    private Object response;

//    @JsonCreator
    public GatewayResponse() {}

    public GatewayResponse(String status, Object response) {
        this.status = status;
        this.response = response;
    }

//    @JsonIgnoreProperties(ignoreUnknown = true)
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
//        @JsonProperty("type")
        private String type;

//        @JsonProperty("message")
        private Object message;

//        @JsonProperty("result")
        private Object result;

//        @JsonCreator
        public Response() {}

        public Response(String type, Object message, Object result) {
            this.type = type;
            this.message = message;
            this.result = result;
        }
    }

    public Object getFailureMsg() {
        if (response instanceof Response) {
            return ((Response) response).message;
        }
        return response;
    }

    public String getStatus(){
        return status;
    }

    public static GatewayResponse failureResponse(String errorType, Object msg){
        Response response = new Response(errorType, msg, null);
        return new GatewayResponse("error", response);
    }

    public static GatewayResponse failureResponse(String errorType, Object msg, Object errorResult){
        Response response = new Response(errorType, msg, errorResult);
        return new GatewayResponse("error", response);
    }

//    public static GatewayResponse failureResponse(GatewayError error) {
//        Response response = new Response(error.getCode(), error.getMsg(), null);
//        return new GatewayResponse("error", response);
//    }

    public static GatewayResponse successResponse(Object response){
        return new GatewayResponse("success", response);
    }

    public static GatewayResponse malformedRequestResponse(String msg){
        if(msg == null){
            msg = "incorrect api request";
        }
        return failureResponse("malformed_request", msg);
    }

    public static GatewayResponse unknownApiResponse(){
        return failureResponse("unknown_api", "unknown api");
    }

    public static GatewayResponse internalErrorResponse(String msg){
        if(msg == null){
            msg = "Internal server error";
        }
        return failureResponse("internal_server_error", msg);
    }

    public static GatewayResponse requestNotAuthenticated(){
        return failureResponse("not_authenticated", "You are not authenticated.");
    }

    public static GatewayResponse uniformResponseWithNetworkResult(Map<String, Object> response) {
        if (NetworkResult.isFailed(response)) {
            String errorCode = NetworkResult.getFailedCode(response);
            String errMsg = NetworkResult.getFailedMessage(response);
            Object errorResult = NetworkResult.getResult(response);
            if (errorCode == null){
                return malformedRequestResponse(errMsg);
            } else if (errorCode.equals("internal_error")){
                return internalErrorResponse(errMsg);
            } else {
                return failureResponse(errorCode, errMsg, errorResult);
            }
        } else if (NetworkResult.isSuccess(response)){
            return successResponse(NetworkResult.getResult(response));
        } else {
            return malformedRequestResponse((String) response.get("unkown error"));
        }
    }

    /**
     * construct a failure GatewayResponse with exception msg,
     * if HttpContext is not null, set httpcode to 422 which means Unprocessable Entity
     */
//    public static GatewayResponse failureResponse(@Nullable HttpContext context, Throwable e) {
//        return failureResponse(context, HttpResponseStatus.UNPROCESSABLE_ENTITY.code(), e);
//    }
//
//    /**
//     * construct a failure GatewayResponse with exception msg,
//     * if HttpContext is not null, set with given httpcode
//     */
//    public static GatewayResponse failureResponse(@Nullable HttpContext context, int httpcode, Throwable e) {
//        HttpResponseStatus respCode = HttpResponseStatus.valueOf(httpcode);
//        return failureResponse(context, respCode, e);
//    }
//
//    /**
//     * construct a failure GatewayResponse with exception msg,
//     * if HttpContext is not null, set with given httpcode
//     */
//    public static GatewayResponse failureResponse(
//            @Nullable HttpContext context, HttpResponseStatus respCode, Throwable e) {
//        e = e instanceof CompletionException ? e.getCause() : e;
//        return failureResponse(context, respCode, e.getMessage());
//    }
//
//    public static GatewayResponse failureResponse(
//            @Nullable HttpContext context, HttpResponseStatus respCode, String msg) {
//        if (context != null) {
//            context.setHttpcode(respCode);
//        }
//        return failureResponse(context, respCode, msg, respCode.reasonPhrase());
//    }
//
//    public static GatewayResponse failureResponse(
//            @Nullable HttpContext context, HttpResponseStatus respCode, Throwable e, String errorType) {
//        e = e instanceof CompletionException ? e.getCause() : e;
//        return failureResponse(context, respCode, e.getMessage(), errorType);
//    }
//
//    public static GatewayResponse failureResponse(
//            @Nullable HttpContext context, HttpResponseStatus respCode, String msg, String errorType) {
//        if (context != null) {
//            context.setHttpcode(respCode);
//        }
//        return GatewayResponse.failureResponse(errorType, msg);
//    }
}
