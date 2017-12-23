package org.cs.parshwabhoomiapp.client.framework.dto;

import android.util.JsonReader;
import android.util.JsonWriter;

/**
 * Created by saurabhATchampasheruDOTbuild on 5/13/2016.
 * Each object that needs to take part in the web restful request/response API or for some reason, needs to
 * be serialized into JSON format shall implement this inferface.
 */
public interface JSONSerializable {
    //Read from the jsonReader provided & populate the fields of the domain object.
    void readJSON(JsonReader jsonReader);

    void writeJSON(JsonWriter jsonWriter);
}
