
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Submission;

@Component
@Transactional
public class SubmissionToStringConverter implements Converter<Submission, String> {

	@Override
	public String convert(final Submission a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}

}
