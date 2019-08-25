
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import repositories.ReviewerRepository;
import domain.Reviewer;

public class StringToReviewerConverter implements Converter<String, Reviewer> {

	@Autowired
	ReviewerRepository	mr;


	@Override
	public Reviewer convert(final String text) {
		Reviewer result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.mr.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}

}
