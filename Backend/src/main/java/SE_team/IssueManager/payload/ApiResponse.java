package SE_team.IssueManager.payload;

import SE_team.IssueManager.payload.code.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    //생성 성공 응답
    public static <T> ApiResponse<T> created(T result) {
        return new ApiResponse<T>(true,"200","created",result);     //임의로 써놓음..수정 필요
    }
    //성공 시
    public static <T> ApiResponse<T> onSuccess(SuccessStatus status,T result) {
        return new ApiResponse<T>(true,status.getCode(),status.getMessage(),result);
    }
    //실패 시
    public static <T> ApiResponse<T> onFailure(String code, String message, T result) {
        return new ApiResponse<T>(false,code,message,result);
    }
}
