
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Paper;

@Component
@Transactional
public class PaperToStringConverter implements Converter<Paper, String> {

	@Override
	public String convert(final Paper a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}

}
