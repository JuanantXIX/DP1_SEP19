
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Topic;

@Component
@Transactional
public class TopicToStringConverter implements Converter<Topic, String> {

	@Override
	public String convert(final Topic a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}

}
