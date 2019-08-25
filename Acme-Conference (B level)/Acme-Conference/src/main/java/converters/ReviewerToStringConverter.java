
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Reviewer;

@Component
@Transactional
public class ReviewerToStringConverter implements Converter<Reviewer, String> {

	@Override
	public String convert(final Reviewer a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}

}
