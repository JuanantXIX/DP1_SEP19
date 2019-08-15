
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tutorial;

@Component
@Transactional
public class TutorialToStringConverter implements Converter<Tutorial, String> {

	@Override
	public String convert(final Tutorial a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}

}
