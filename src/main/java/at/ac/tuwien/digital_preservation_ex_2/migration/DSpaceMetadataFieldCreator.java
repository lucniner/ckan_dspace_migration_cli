package at.ac.tuwien.digital_preservation_ex_2.migration;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceMetadataField;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class DSpaceMetadataFieldCreator {

    private final HttpHeaders headers = new HttpHeaders();
    private final String url;
    private final RestTemplate restTemplate;

    public DSpaceMetadataFieldCreator(
            final DSpaceConfigProperties dSpaceConfigProperties, final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        final String baseUrl =
                dSpaceConfigProperties
                        .getProtocol()
                        .concat(dSpaceConfigProperties.getHost())
                        .concat(":")
                        .concat(dSpaceConfigProperties.getPort());
        final String path = "rest/registries/schema/dc/metadata-fields";
        this.url = baseUrl.concat(path);

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(HttpHeaders.COOKIE, SessionHolder.getSession());
    }

    public DSpaceMetadataField createMetadataField(final DSpaceMetadataField dSpaceMetadataField) {
        HttpEntity<DSpaceMetadataField> entity = new HttpEntity<>(dSpaceMetadataField, headers);

        ResponseEntity<DSpaceMetadataField> result =
                restTemplate.exchange(url, HttpMethod.POST, entity, DSpaceMetadataField.class);
        return result.getBody();
    }
}
