package darkdata.transformers;

import darkdata.model.kb.g4.G4Service;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author szednik
 */

@Component
public class G4ServiceConverter implements Converter<G4Service, Optional<String>> {

    @Override
    public Optional<String> convert(G4Service service) {
        return service.getIdentifier();
    }
}
