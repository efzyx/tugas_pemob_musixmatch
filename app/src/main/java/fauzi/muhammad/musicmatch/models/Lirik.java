
package fauzi.muhammad.musicmatch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lirik {

    @SerializedName("message")
    @Expose
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
