package cox.automotive.challenge.utill;

import com.google.gson.Gson;
import cox.automotive.challenge.data.DataSet;
import cox.automotive.challenge.data.VehicleIds;
import lombok.NonNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static cox.automotive.challenge.utill.Endpoints.ANSWER_API;
import static cox.automotive.challenge.utill.Endpoints.DATA_SET_API;
import static cox.automotive.challenge.utill.Endpoints.VEHICLE_IDS_API;

public class RestHelper {
    @NonNull
    private final RestTemplate restTemplate;

    public RestHelper(final RestTemplate template) {
        this.restTemplate = template;
    }

    public String submitAnswer(final String dataSetId, final String asnwer) {

        // HttpHeaders headers = new HttpHeaders();
        // // RestTemplate restTemplate = new RestTemplate();
        // headers.setContentType(MediaType.APPLICATION_JSON);
        // MultiValueMap<String, String> parameters= new LinkedMultiValueMap<String, String>(); 
        // parameters.add(Endpoints.Paths.DATASET_ID, dataSetId);
        // HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
        // String completeRez =restTemplate.exchange(ANSWER_API, HttpMethod.POST, requestEntity, String.class, parameters).getBody();


        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final Map<String, String> paramsForAnswer = new HashMap<>();
        paramsForAnswer.put(Endpoints.Paths.DATASET_ID, dataSetId);

        final HttpEntity<String> entity = new HttpEntity<>(asnwer, headers);
        //ResponseEntity<String> res=restTemplate.exchange(ANSWER_API, HttpMethod.POST, entity, String.class, paramsForAnswer);
        ResponseEntity<String> res=restTemplate.postForEntity(ANSWER_API , entity , String.class);

        
        String returnval=res.getBody();

        return returnval;
    }

    public VehicleIds getVehicleIds(final String dataSetId) {
        final Gson g = new Gson();
        // Get Vehicle IDs for the data set <FAST>
        final Map<String, Object> params = new HashMap<>();
        params.put(Endpoints.Paths.DATASET_ID, dataSetId);
        final String jsonVechIds = restTemplate.getForObject(VEHICLE_IDS_API, String.class, params);

        return g.fromJson(jsonVechIds, VehicleIds.class);
    }

    public String getDataSetId() {
        return Objects.requireNonNull(restTemplate.getForObject(DATA_SET_API, DataSet.class)).getDataSetId();
    }
}
