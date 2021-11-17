package story;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class Chapter {
	private String flag = "0";    //last chapter
	private String chapter = null;    //name
	private String place = null;    //location
	private String lat = null;        //latitude
	private String lon = null;        //longitude
	private String image = "";    //background image
	private LinkedList<String> createArgs;    //list of global artefacts to create chap
	private LinkedList<String> destroyArgs;    //list of args which deletes this chapter
	private DialogList dList = null;//dialogue list of this chapter
	private String type = null;        //sql/ans
	private String question = null;    //riddle question
	private String answer = null;		//riddle answer
	private Hint hints = null;        //hints and costs
	private String artefact = null;

	private boolean isEdited = false;


	public Chapter() {
	}

	public JSONObject toJSONObject(){
		JSONObject chapter = new JSONObject();
		chapter.put("flag",this.flag);
		chapter.put("chapter",this.chapter);
		chapter.put("place",this.place);
		chapter.put("latitude",this.lat);
		chapter.put("longitude",this.lon);
		chapter.put("image",this.image);
		chapter.put("dependency",new JSONArray(this.createArgs));
		chapter.put("TTL",new JSONArray(this.destroyArgs));
		chapter.put("dialogue", this.dList.getJSONarray());
		chapter.put("type",this.type);
		chapter.put("question", this.question);
		chapter.put("answer", this.answer);
		chapter.put("hints", this.hints.getJSON());
		chapter.put("artefact", new JSONArray(this.artefact.split(", ")));

		return chapter;
	}

	public void setEdited(){
		isEdited = true;
	}

	public boolean isEdited(){
		return isEdited;
	}

	@Override
	public String toString(){
		return String.format("%-20s  %-25s  %-15s  %-30s  %-30s  %s",
				chapter, place, artefact, createArgs.toString(), destroyArgs.toString(), type);
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LinkedList<String> getCreateArgs() {
		return createArgs;
	}

	public void setCreateArgs(LinkedList<String> createArgs) {
		this.createArgs = createArgs;
	}

	public LinkedList<String> getDestroyArgs() {
		return destroyArgs;
	}

	public void setDestroyArgs(LinkedList<String> destroyArgs) {
		this.destroyArgs = destroyArgs;
	}

	public DialogList getdList() {
		return dList;
	}

	public void setdList(DialogList dList) {
		this.dList = dList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Hint getHints() {
		return hints;
	}

	public void setHints(Hint hints) {
		this.hints = hints;
	}

	public String getArtefact() {
		return artefact;
	}

	public void setArtefact(String artefact) {
		this.artefact = artefact;
	}

	public String getFlag() { return this.flag;	}

	@Override
	public int hashCode(){
		int hash = flag.hashCode() * chapter.hashCode();
		hash += place.hashCode() * lat.hashCode() * lon.hashCode();
		hash *= question.hashCode() * answer.hashCode();
		hash %= 199;
		return hash;
	}

	@Override
	public boolean equals(Object i){
		if(i == null || getClass() != i.getClass())	return false;
		if(!flag.equals(((Chapter) i).getFlag())) return false;
		if(!chapter.equals(((Chapter) i).getChapter())) return false;
		if(!place.equals(((Chapter) i).getPlace())) return false;
		if(!lat.equals(((Chapter) i).getLat())) return false;
		if(!lon.equals(((Chapter) i).getLon())) return false;
		if(!image.equals(((Chapter) i).getImage())) return false;
		if(createArgs != ((Chapter) i).getCreateArgs())  return false;
		if(destroyArgs != ((Chapter) i).getDestroyArgs())  return false;
		if(!type.equals(((Chapter) i).getType()))  return false;
		if(!question.equals(((Chapter) i).getQuestion()))  return false;
		if(!answer.equals(((Chapter) i).getAnswer()))  return false;
		if(!artefact.equals(((Chapter) i).getArtefact()))  return false;

		return true;
	}
}