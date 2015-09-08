package dds.javatar.app.dto.receta.util.transformer;

import spark.ResponseTransformer;
import com.google.gson.Gson;

public class JsonTransformer implements ResponseTransformer {

	private Gson gson;

	public JsonTransformer(Gson gson) {
		this.gson = gson;
	}

	@Override
	public String render(Object model) {
		return this.gson.toJson(model);
	}

}
