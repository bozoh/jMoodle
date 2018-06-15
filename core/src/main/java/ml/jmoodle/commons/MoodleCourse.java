/*
 *  Copyright (C) 2012 Bill Antonia
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package ml.jmoodle.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ml.jmoodle.annotations.MoodleConverter;



/**
 * <p>
 * Class for MoodleCourse objects. Used when creating new courses or retrieving
 * course details.
 * </p>
 * <p>
 * When creating a new MoodleCourse object its initial id state is set to
 * default of -1. This will be updated to the id of the course created in
 * Moodle.
 * </p>
 * <p>
 * For Moodle course creation a minimum of attributes shortname, fullname and
 * categoryid have to be set programatically, other required attributes are set
 * to default values found in Moodle, change as appropriate using setter methods
 * before a call for course creation.
 * </p>
 * 
 * @see MoodleRestCourse
 * @author Bill Antonia
 * @author carlosalexandre
 *
 */
@MoodleConverter
public class MoodleCourse implements Serializable, Comparable<MoodleCourse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4858770441492634631L;
	/**
	 *
	 */
	public static final int SUMMARY_FORMAT_NO = 0;
	/**
	 *
	 */
	public static final int SUMMARY_FORMAT_YES = 1;
	/**
	 *
	 */
	public static final String FORMAT_TOPICS = "topics";
	/**
	 *
	 */
	public static final String FORMAT_SCORM = "scorm";
	/**
	 *
	 */
	public static final String FORMAT_WEEKS = "weeks";
	/**
	 *
	 */
	public static final String FORMAT_SOCIAL = "social";
	/**
	 *
	 */
	public static final int SHOW_GRADES_NO = 0;
	/**
	 *
	 */
	public static final int SHOW_GRADES_YES = 1;
	/**
	 *
	 */
	public static final int SHOW_REPORTS_NO = 0;
	/**
	 *
	 */
	public static final int SHOW_REPORTS_YES = 1;
	/**
	 *
	 */
	public static final int HIDDEN_SECTIONS_COLLAPSED = 0;
	/**
	 *
	 */
	public static final int HIDDEN_SECTIONS_INVISIBLE = 1;
	/**
	 *
	 */
	public static final int GROUP_MODE_NO_GROUPS = 0;
	/**
	 *
	 */
	public static final int GROUP_MODE_SEPARATE_GROUPS = 1;
	/**
	 *
	 */
	public static final int GROUP_MODE_VISIBLE_GROUPS = 2;
	/**
	 *
	 */
	public static final int GROUP_MODE_FORCE_NO = 0;
	/**
	 *
	 */
	public static final int GROUP_MODE_FORCE_YES = 1;
	/**
	 *
	 */
	public static final boolean COURSE_VISIBLE_TO_STUDENTS_NO = false;
	/**
	 *
	 */
	public static final boolean COURSE_VISIBLE_TO_STUDENTS_YES = true;
	/**
	 *
	 */
	public static final boolean COMPLETION_DISABLED = false;
	/**
	 *
	 */
	public static final boolean COMPLETION_ENABLED = true;
	/**
	 *
	 */
	public static final boolean COMPLETION_NOTIFY_DISABLED = false;
	/**
	 *
	 */
	public static final boolean COMPLETION_NOTIFY_ENABLED = true;
	/**
	 *
	 */
	public static final boolean COMPLETION_ON_ENROLL_DISABLED = false;
	/**
	 *
	 */
	public static final boolean COMPLETION_ON_ENROLL_ENABLED = true;

	private Long id = null;

	// Required for creation, need to be set before call!!!!!!!!
	private String shortname = null;
	private String fullname = null;
	private Long categoryid = null;

	// Required for course creation but will use the default values below unless
	// already changed
	// private Integer summaryformat=SUMMARY_FORMAT_NO;
	private DescriptionFormat summaryformat = DescriptionFormat.MOODLE;
	private String format = FORMAT_TOPICS;
	private Integer showgrades = SHOW_GRADES_YES;
	private Integer newsitems = 1;
	private Integer numsections = 1;
	private Long maxbytes = 0L;
	private Integer showreports = SHOW_REPORTS_NO;
	private Integer hiddensections = HIDDEN_SECTIONS_COLLAPSED;
	private Integer groupmode = GROUP_MODE_NO_GROUPS;
	private Integer groupmodeforce = GROUP_MODE_FORCE_NO;
	private Long defaultgroupingid = 0L;

	// Optional but will be set to these default values during creation
	private Boolean enablecompletion = COMPLETION_DISABLED;
	private Boolean completionstartonenrol = COMPLETION_ON_ENROLL_DISABLED;
	private Boolean completionnotify = COMPLETION_NOTIFY_DISABLED;
	private Boolean visible = COURSE_VISIBLE_TO_STUDENTS_YES;

	// Not used during creation
	private Integer categorysortorder = null;
	private Long timecreated = null;
	private Long timemodified = null;

	// Optional for creation
	private String summary = null;
	private String idnumber = null;
	private String lang = null;
	private String forcetheme = null;
	private Long startdate = null;

	// Other stuff!!!
	private Long enrolledusercount = null;

	private List<OptionParameter> courseformatoptions = null;

	/**
	 * <p>
	 * MoodleCourse, course object constructor with the minimum attribute
	 * settings to create a Moodle course.
	 * </p>
	 * <p>
	 * Note there are a number of required and optional attributes which are set
	 * during the construction, change these as appropriate.
	 * </p>
	 * 
	 * @param shortname
	 *            String
	 * @param fullname
	 *            String
	 * @param categoryid
	 *            long
	 */
	public MoodleCourse(String shortname, String fullname, long categoryid) {
		this.shortname = shortname;
		this.fullname = fullname;
		this.categoryid = categoryid;
	}

	/**
	 * <p>
	 * MoodleCourse object constructor setting the course id.
	 * </p>
	 * <p>
	 * Object used to retrieve the attributes or update a course already in
	 * Moodle.
	 * </p>
	 * 
	 * @param id
	 *            long
	 */
	public MoodleCourse(long id) {
		this.id = id;
	}

	/**
	 * <p>
	 * MoodleCourse object constructor for bean requirements.
	 * </p>
	 */
	public MoodleCourse() {
	}

	public MoodleCourse clean() {
		id = null;
		shortname = null;
		fullname = null;
		categoryid = null;
		summaryformat = null;
		format = null;
		showgrades = null;
		newsitems = null;
		numsections = null;
		maxbytes = null;
		showreports = null;
		hiddensections = null;
		groupmode = null;
		groupmodeforce = null;
		defaultgroupingid = null;
		enablecompletion = null;
		completionstartonenrol = null;
		completionnotify = null;
		visible = null;
		categorysortorder = null;
		timecreated = null;
		timemodified = null;
		summary = null;
		idnumber = null;
		lang = null;
		forcetheme = null;
		startdate = null;
		enrolledusercount = null;
		courseformatoptions = null;
		return this;
	}

	/**
	 * <p>
	 * setMoodleCourseField sets an attribute with type conversion from the name
	 * of the attribute and a value, both passed as strings.
	 * </p>
	 * 
	 * @param nodeName
	 *            String
	 * @param content
	 *            String
	 */
	public void setMoodleCourseField(String nodeName, String content) {
		if (nodeName.equals("id"))
			setId(Long.parseLong(content.trim()));
		if (nodeName.equals("shortname"))
			setShortname(content);
		if (nodeName.equals("categoryid"))
			setCategoryId(Long.parseLong(content.trim()));
		if (nodeName.equals("categorysortorder"))
			setCategorySortOrder(Integer.parseInt(content.trim()));
		if (nodeName.equals("fullname"))
			setFullname(content);
		if (nodeName.equals("idnumber"))
			setIdNumber(content);
		if (nodeName.equals("summary"))
			setSummary(content);
		if (nodeName.equals("summaryformat"))
			setSummaryFormat(Integer.parseInt(content));
		if (nodeName.equals("format"))
			setFormat(content);
		if (nodeName.equals("showgrades"))
			setShowGrades(Integer.parseInt(content));
		if (nodeName.equals("newsitems"))
			setNewsItems(Integer.parseInt(content));
		if (nodeName.equals("startdate"))
			setStartDate(Long.parseLong(content.trim()));
		if (nodeName.equals("numsections"))
			setNumSections(Integer.parseInt(content));
		if (nodeName.equals("maxbytes"))
			setMaxBytes(Long.parseLong(content.trim()));
		if (nodeName.equals("showreports"))
			setShowReports(Integer.parseInt(content));
		if (nodeName.equals("visible"))
			setVisible(Integer.parseInt(content) == 0 ? COURSE_VISIBLE_TO_STUDENTS_NO : COURSE_VISIBLE_TO_STUDENTS_YES);
		if (nodeName.equals("hiddensections") && !content.isEmpty())
			setHiddenSections(Integer.parseInt(content));
		if (nodeName.equals("groupmode"))
			setGroupMode(Integer.parseInt(content));
		if (nodeName.equals("groupmodeforce"))
			setGroupModeForce(Integer.parseInt(content));
		if (nodeName.equals("defaultgroupingid"))
			setDefaultGroupingId(Long.parseLong(content.trim()));
		if (nodeName.equals("timecreated"))
			setTimeCreated(Long.parseLong(content.trim()));
		if (nodeName.equals("timemodified"))
			setTimeModified(Long.parseLong(content.trim()));
		if (nodeName.equals("enablecompletion"))
			setEnableCompletion(Integer.parseInt(content) == 0 ? COMPLETION_DISABLED : COMPLETION_ENABLED);
		if (nodeName.equals("completionstartonenrol"))
			setCompletionStartOnEnrol(
					Integer.parseInt(content) == 0 ? COMPLETION_ON_ENROLL_DISABLED : COMPLETION_ON_ENROLL_ENABLED);
		if (nodeName.equals("completionnotify"))
			setCompletionNotify(
					Integer.parseInt(content) == 0 ? COMPLETION_NOTIFY_DISABLED : COMPLETION_NOTIFY_ENABLED);
		if (nodeName.equals("lang"))
			setLang(content);
		if (nodeName.equals("forcetheme"))
			setForceTheme(content);
		if (nodeName.equals("enrolledusercount"))
			setEnrolledUserCount(Long.parseLong(content.trim()));
	}

	public OptionParameter[] getCourseformatoptions() {
		OptionParameter[] results = null;
		if (courseformatoptions != null) {
			if (courseformatoptions.size() > 0) {
				results = new OptionParameter[courseformatoptions.size()];
				courseformatoptions.toArray(results);
			}
		}
		return results;
	}

	public void setCourseformatoptions(List<OptionParameter> courseformatoptions) {
		this.courseformatoptions = courseformatoptions;
	}


	public void setCourseformatoptions(OptionParameter[] courseformatoptions) {
		if (this.courseformatoptions == null)
			this.courseformatoptions = new ArrayList<OptionParameter>();
		for (int i = 0; i < courseformatoptions.length; i++) {
			this.courseformatoptions.add(courseformatoptions[i]);
		}
	}

	public void addCourseformatoptions(OptionParameter courseformatoption) {
		if (this.courseformatoptions == null)
			this.courseformatoptions = new ArrayList<OptionParameter>();
		this.courseformatoptions.add(courseformatoption);
	}

	/**
	 * <p>
	 * Method to set the course id in a MoodleCourse object.
	 * </p>
	 * 
	 * @param id
	 *            long
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Method to set the shortname of a course in a MoodleCourse object.
	 * </p>
	 * <p>
	 * Required to be set on course creation. Default null, error on course
	 * creation if null.
	 * </p>
	 * 
	 * @param shortname
	 *            String
	 */
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	/**
	 * Method to set the category id of a course in a MoodleCourse object.
	 * Required to be set on course creation. Default -1, error on course
	 * creation.
	 * 
	 * @param categoryid
	 *            long
	 */
	public void setCategoryId(Long categoryid) {
		this.categoryid = categoryid;
	}

	/**
	 * Method to set the category sort order for a course. Default -1, do not
	 * set, use Moodle default.
	 * 
	 * @param categorysortorder
	 *            long
	 */
	public void setCategorySortOrder(Integer categorysortorder) {
		this.categorysortorder = categorysortorder;
	}

	/**
	 * Method to set the fullname of a course in a MoodleCourse object. Required
	 * to be set on course creation. Default null, error on course creation.
	 * 
	 * @param fullname
	 *            String
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Method to set the idnumber attribute of the MoodleCourse object. Note
	 * idnumber does not have to be a number, it can be a string. Default null.
	 * 
	 * @param idnumber
	 *            String
	 */
	public void setIdNumber(String idnumber) {
		this.idnumber = idnumber;
	}

	/**
	 * Method to set the Moodle course summary field of the MoodleCourse object.
	 * A summary of the course. Default null, no summary set.
	 * 
	 * @param summary
	 *            String
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * Method to set the summaryformat attribute of a MoodleCourse object. Not
	 * sure what this field does as it seems to have no equivalent in the course
	 * creation form within Moodle. Default 0 or off.
	 * 
	 * @param summaryformat
	 *            int
	 */
	public void setSummaryFormat(Integer summaryformat) {
		switch (summaryformat) {
		case 0:
			this.summaryformat = DescriptionFormat.MOODLE;
			break;
		case 1:
			this.summaryformat = DescriptionFormat.HTML;
			break;
		case 2:
			this.summaryformat = DescriptionFormat.PLAIN;
			break;
	
		// case 3: //Bug not have this value, MARKDOWN = 4
		// 	this.summaryformat = DescriptionFormat.MARKDOWN;
		// 	break;

		case 4:
			this.summaryformat = DescriptionFormat.MARKDOWN;
			break;
		}
	}

	public void setSummaryDescriptionFormat(DescriptionFormat summaryformat) {
		this.summaryformat = summaryformat;
	}

	/**
	 * Method to set the format of the MoodleCouse object "topics", "weekly",
	 * "social" or "scorm". There is also a "site" format but there should be
	 * only one course within the database, this is "Site home". Default
	 * "topics".
	 * 
	 * @param format
	 *            String
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * Method to set the showgrades attribute of a MoodleCourse object. Although
	 * this could be a boolean, the Moodle database stores this in a field which
	 * has more than on bit. Default 1 or "Yes".
	 * 
	 * @param showgrades
	 *            int
	 */
	public void setShowGrades(Integer showgrades) {
		this.showgrades = showgrades;
	}

	/**
	 * Method to set the MoodleCourse object newsitems attribute. The number of
	 * news items to show in a Moodle course. Default 1.
	 * 
	 * @param newsitems
	 *            int
	 */
	public void setNewsItems(Integer newsitems) {
		this.newsitems = newsitems;
	}

	/**
	 * Method to set the start date of a MoodleCourse object as a Unix
	 * timestamp. Default -1, no start date set.
	 * 
	 * @param startdate
	 *            long
	 */
	public void setStartDate(Long startdate) {
		this.startdate = startdate;
	}

	/**
	 * Method to set the numsections attribute of a MoodleCourse object. Sets
	 * the number of weeks/topics of a Moodle course. Default 1.
	 * 
	 * @param numsections
	 *            int
	 */
	public void setNumSections(Integer numsections) {
		this.numsections = numsections;
	}

	/**
	 * Method to set the maxbytes attribute of a MoodleCourse object. Sets the
	 * size in bytes the maximum allowed transfer size for files within the
	 * course.
	 * 
	 * @param maxbytes
	 *            long
	 */
	public void setMaxBytes(Long maxbytes) {
		this.maxbytes = maxbytes;
	}

	/**
	 * Method to set the showreports attribute of a MoodleCourse object. Flag to
	 * be able to set to enable reports to be visible to students. Although this
	 * could be a boolean, the Moodle database stores this in a field which has
	 * more than on bit. Default 0 or off.
	 * 
	 * @param showreports
	 *            int
	 */
	public void setShowReports(Integer showreports) {
		this.showreports = showreports;
	}

	/**
	 * Method to set the visible attribute of a MoodleCourse object. Sets the
	 * visibility of a Moodle course for students,
	 * "This course is not available to students" false,
	 * "This course is available to students" true. Default true or visible to
	 * students.
	 * 
	 * @param visible
	 *            boolean
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	/**
	 * Method to set the attribute hiddensections in a MoodleCourse object. The
	 * hidden sections make sections, i.e. topics or weeks in a course either
	 * collapsed 0 or completely invisible 1. Default is 0 or
	 * "Hidden sections are shown in collapsed form".
	 * 
	 * @param hiddensections
	 *            int
	 */
	public void setHiddenSections(Integer hiddensections) {
		this.hiddensections = hiddensections;
	}

	/**
	 * Method to set the attribute groupmode in a MoodleCourse object. Group
	 * mode in a Moodle course can be set to "No groups" 0, "Separate groups" 1
	 * or "Visible groups" 2. Default 0 or "No groups".
	 * 
	 * @param groupmode
	 *            int
	 */
	public void setGroupMode(Integer groupmode) {
		this.groupmode = groupmode;
	}

	/**
	 * Method to set the attribute groupmodeforce in a MoodleCourse object. Sets
	 * groups to be used within every activity of a course. Default 0 or "No".
	 * 
	 * @param groupmodeforce
	 *            int
	 */
	public void setGroupModeForce(Integer groupmodeforce) {
		this.groupmodeforce = groupmodeforce;
	}

	/**
	 * Method to set the attribute defaultgroupingid of a MoodleCourse object.
	 * Not sure what it does at the moment. Default 0.
	 * 
	 * @param defaultgroupingid
	 *            long
	 */
	public void setDefaultGroupingId(Long defaultgroupingid) {
		this.defaultgroupingid = defaultgroupingid;
	}

	/**
	 * Method to set the attribute timecreated of a MoodleCourse object. UNIX
	 * timestamp of the date the course is created. Default -1 or do not use
	 * during course creation.
	 * 
	 * @param timecreated
	 *            long
	 */
	public void setTimeCreated(Long timecreated) {
		this.timecreated = timecreated;
	}

	/**
	 * Method to set the attribute timemodified of a MoodleCourse object. UNIX
	 * timestamp of the date the course was modified. Default -1 or do not use
	 * during course creation.
	 * 
	 * @param timemodified
	 *            long
	 */
	public void setTimeModified(Long timemodified) {
		this.timemodified = timemodified;
	}

	/**
	 * Method to set the attribute enablecompletion of a MoodleCourse object.
	 * 
	 * @param enablecompletion
	 *            boolean
	 */
	public void setEnableCompletion(Boolean enablecompletion) {
		this.enablecompletion = enablecompletion;
	}

	/**
	 * Method to set the attribute completionstartonenrol of a MoodleCourse
	 * object. Note completion has to be enabled first in Settings->site
	 * administration->Advanced settings. Default disabled.
	 * 
	 * @param completionstartonenrol
	 *            boolean
	 */
	public void setCompletionStartOnEnrol(Boolean completionstartonenrol) {
		this.completionstartonenrol = completionstartonenrol;
	}

	/**
	 * Method to set the attribute completionnotify of a MoodleCourse object.
	 * Note completion has to be enabled first in Settings->site
	 * administration->Advanced settings. Default disabled.
	 * 
	 * @param completionnotify
	 *            boolean
	 */
	public void setCompletionNotify(Boolean completionnotify) {
		this.completionnotify = completionnotify;
	}

	/**
	 * Method to set the language of the Moodle course. Default is null, use the
	 * installed default language.
	 * 
	 * @param lang
	 *            String
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * Method to set the forcetheme attribute of a MoodleCourse object. Used to
	 * set the theme of a course.
	 * 
	 * @param forcetheme
	 *            String
	 */
	public void setForceTheme(String forcetheme) {
		this.forcetheme = forcetheme;
	}

	/**
	 * Method to set the enrolledusercount attribute of a MoodleCourse object.
	 * 
	 * @param enrolledusercount
	 *            long
	 */
	private void setEnrolledUserCount(Long enrolledusercount) {
		this.enrolledusercount = enrolledusercount;
	}

	/**
	 * Method to get the id of a Moodle course from a MoodleCourse object.
	 * 
	 * @return id long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Method to get the shortname of a Moodle course from a MoodleCourse
	 * object.
	 * 
	 * @return shortname String
	 */
	public String getShortname() {
		return shortname;
	}

	/**
	 * Method to get the categoryid of a Moodle course from a MoodleCourse
	 * object.
	 * 
	 * @return categoryid long
	 */
	public Long getCategoryId() {
		return categoryid;
	}

	/**
	 * Method to return the categorysortorder attribute of a MoodleCourse
	 * object.
	 * 
	 * @return categorysortorder
	 */
	public Integer getCategorySortOrder() {
		return categorysortorder;
	}

	/**
	 * Method to get the fullname attribute of a MoodleCourse object.
	 * 
	 * @return fullname String
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Method to get the idnumber attribute of a MoodleCourse object.
	 * 
	 * @return idnumber String
	 */
	public String getIdNumber() {
		return idnumber;
	}

	/**
	 * Method to get the summary attribute of a MoodleCourse object.
	 * 
	 * @return summary String
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Method to get the summaryformat attribute of a MoodleCourse object.
	 * 
	 * @return summaryformat int
	 */
	public Integer getSummaryFormat() {
		return summaryformat.toInt();
	}

	public DescriptionFormat getSummaryDescriptionFormat() {
		return summaryformat;
	}

	/**
	 * Method to get the course format attribute of a MoodleCourse object
	 * 
	 * @return format String
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Method to get the status of the showgrades attribute of a MoodleCourse
	 * object.
	 * 
	 * @return showgrades int
	 */
	public Integer getShowGrades() {
		return showgrades;
	}

	/**
	 * Method to get the number of news items from the newsitems attribute in a
	 * MoodleCourse object
	 * 
	 * @return newsitems int
	 */
	public Integer getNewsItems() {
		return newsitems;
	}

	/**
	 * Method to get the startdate attribute of a MoodleCourse object. Unix
	 * timestamp.
	 * 
	 * @return startdate long
	 */
	public Long getStartDate() {
		return startdate;
	}

	/**
	 * Method to get the numsections attribute of a MoodleCourse object. Gets
	 * the number of sections in a Moodle course.
	 * 
	 * @return numsections int
	 */
	public Integer getNumSections() {
		return numsections;
	}

	/**
	 * Method to get the maxbytes attribute in a MoodleCourse object. This is
	 * the maximum number of bytes allowed to be transferred during an upload to
	 * the course.
	 * 
	 * @return maxbytes long
	 */
	public Long getMaxBytes() {
		return maxbytes;
	}

	/**
	 * Method to get the showreports attribute in a MoodleCourse object. Returns
	 * an integer representing the status of students being allowed to view
	 * reports.
	 * 
	 * @return showreports int
	 */
	public Integer getShowReports() {
		return showreports;
	}

	/**
	 * Is the course visible to students?
	 * 
	 * @return visible boolean
	 */
	public Boolean isVisibleToStudents() {
		return visible;
	}

	/**
	 * Method to get the visible attribute of a MoodleCourse object.
	 * 
	 * @return visible boolean
	 */
	public Boolean isVisible() {
		return visible;
	}

	public Integer getVisible() {
		return visible ? 1 : 0;
	}

	/**
	 * Method to get the hiddensections attribute of a MoodleCourse object.
	 * Value returned, 0 hidden sections are in a collapsed state, 1 hidden
	 * sections are invisible.
	 * 
	 * @return hiddensections int
	 */
	public Integer getHiddenSections() {
		return hiddensections;
	}

	/**
	 * Method to get the groupmode attribute of a MoodleCourse object. Value
	 * returned, 0 no groups, 1 separate groups, 2 visible groups.
	 * 
	 * @return group mode int
	 */
	public Integer getGroupMode() {
		return groupmode;
	}

	/**
	 * Method to get the groupmodeforce attribute of a MoodleCourse object.
	 * 
	 * @return groupmodeforce int
	 */
	public Integer getGroupModeForce() {
		return groupmodeforce;
	}

	/**
	 * Method to get the defaultgroupingid attribute of a MoodleCourse object.
	 * 
	 * @return defaultgroupingid long
	 */
	public Long getDefaultGroupingId() {
		return defaultgroupingid;
	}

	/**
	 * Method to get the timecreated attribute of a MoodleCourse object.
	 * 
	 * @return timecreated long
	 */
	public Long getTimeCreated() {
		return timecreated;
	}

	/**
	 * Method to get the timemodified attribute of a MoodleCourse object.
	 * 
	 * @return timemodified long
	 */
	public Long getTimeModified() {
		return timemodified;
	}

	/**
	 * Method to get the enablecompletion attribute of a MoodleCourse object.
	 * 
	 * @return enablecompletion boolean
	 */
	public Boolean isEnableCompletion() {
		return enablecompletion;
	}

	public Integer getEnableCompletion() {
		return enablecompletion ? 1 : 0;
	}

	/**
	 * Method to get the completionstartonenrol attribute of a MoodleCourse
	 * object.
	 * 
	 * @return completionstartonenrol boolean
	 */
	public Boolean isCompletionStartOnEnrol() {
		return completionstartonenrol;
	}

	public Integer getCompletionStartOnEnrol() {
		return completionstartonenrol ? 1 : 0;
	}

	/**
	 * Method to get the completionnotify attribute of a MoodleCourse object.
	 * 
	 * @return completionnotify boolean
	 */
	public Boolean isCompletionNotify() {
		return completionnotify;
	}


	public Integer getCompletionNotify() {
		return completionnotify ? 1 : 0;
	}

	/**
	 * Method to get the lang attribute of a MoodleCourse object.
	 * 
	 * @return lang String
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * Method to get the forcetheme attribute of a MoodleCourse object.
	 * 
	 * @return forcetheme String
	 */
	public String getForceTheme() {
		return forcetheme;
	}

	/**
	 * Method to get the enrolledusercount attribute of a MoodleCourse object.
	 * 
	 * @return enrolledusercount long
	 */
	public Long getEnrolledUserCount() {
		return enrolledusercount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MoodleCourse [id=").append(id).append(", shortname=").append(shortname).append(", fullname=")
				.append(fullname).append(", categoryid=").append(categoryid).append(", summaryformat=")
				.append(summaryformat).append(", format=").append(format).append(", showgrades=").append(showgrades)
				.append(", newsitems=").append(newsitems).append(", numsections=").append(numsections)
				.append(", maxbytes=").append(maxbytes).append(", showreports=").append(showreports)
				.append(", hiddensections=").append(hiddensections).append(", groupmode=").append(groupmode)
				.append(", groupmodeforce=").append(groupmodeforce).append(", defaultgroupingid=")
				.append(defaultgroupingid).append(", enablecompletion=").append(enablecompletion)
				.append(", completionstartonenrol=").append(completionstartonenrol).append(", completionnotify=")
				.append(completionnotify).append(", visible=").append(visible).append(", categorysortorder=")
				.append(categorysortorder).append(", timecreated=").append(timecreated).append(", timemodified=")
				.append(timemodified).append(", summary=").append(summary).append(", idnumber=").append(idnumber)
				.append(", lang=").append(lang).append(", forcetheme=").append(forcetheme).append(", startdate=")
				.append(startdate).append(", enrolledusercount=").append(enrolledusercount)
				.append(", courseformatoptions=").append(courseformatoptions).append("]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryid == null) ? 0 : categoryid.hashCode());
		result = prime * result + ((categorysortorder == null) ? 0 : categorysortorder.hashCode());
		result = prime * result + ((completionnotify == null) ? 0 : completionnotify.hashCode());
		result = prime * result + ((completionstartonenrol == null) ? 0 : completionstartonenrol.hashCode());
		result = prime * result + ((courseformatoptions == null) ? 0 : courseformatoptions.hashCode());
		result = prime * result + ((defaultgroupingid == null) ? 0 : defaultgroupingid.hashCode());
		result = prime * result + ((enablecompletion == null) ? 0 : enablecompletion.hashCode());
		result = prime * result + ((enrolledusercount == null) ? 0 : enrolledusercount.hashCode());
		result = prime * result + ((forcetheme == null) ? 0 : forcetheme.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + ((groupmode == null) ? 0 : groupmode.hashCode());
		result = prime * result + ((groupmodeforce == null) ? 0 : groupmodeforce.hashCode());
		result = prime * result + ((hiddensections == null) ? 0 : hiddensections.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idnumber == null) ? 0 : idnumber.hashCode());
		result = prime * result + ((lang == null) ? 0 : lang.hashCode());
		result = prime * result + ((maxbytes == null) ? 0 : maxbytes.hashCode());
		result = prime * result + ((newsitems == null) ? 0 : newsitems.hashCode());
		result = prime * result + ((numsections == null) ? 0 : numsections.hashCode());
		result = prime * result + ((shortname == null) ? 0 : shortname.hashCode());
		result = prime * result + ((showgrades == null) ? 0 : showgrades.hashCode());
		result = prime * result + ((showreports == null) ? 0 : showreports.hashCode());
		result = prime * result + ((startdate == null) ? 0 : startdate.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		result = prime * result + ((summaryformat == null) ? 0 : summaryformat.hashCode());
		result = prime * result + ((timecreated == null) ? 0 : timecreated.hashCode());
		result = prime * result + ((timemodified == null) ? 0 : timemodified.hashCode());
		result = prime * result + ((visible == null) ? 0 : visible.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoodleCourse other = (MoodleCourse) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idnumber == null) {
			if (other.idnumber != null)
				return false;
		} else if (!idnumber.equals(other.idnumber))
			return false;
		if (categoryid == null) {
			if (other.categoryid != null)
				return false;
		} else if (!categoryid.equals(other.categoryid))
			return false;
		if (categorysortorder == null) {
			if (other.categorysortorder != null)
				return false;
		} else if (!categorysortorder.equals(other.categorysortorder))
			return false;
		if (completionnotify == null) {
			if (other.completionnotify != null)
				return false;
		} else if (!completionnotify.equals(other.completionnotify))
			return false;
		if (completionstartonenrol == null) {
			if (other.completionstartonenrol != null)
				return false;
		} else if (!completionstartonenrol.equals(other.completionstartonenrol))
			return false;
		// if (courseformatoptions == null) {
		// 	if (other.courseformatoptions != null)
		// 		return false;
		// } else if (!courseformatoptions.equals(other.courseformatoptions))
		// 	return false;
		if (defaultgroupingid == null) {
			if (other.defaultgroupingid != null)
				return false;
		} else if (!defaultgroupingid.equals(other.defaultgroupingid))
			return false;
		if (enablecompletion == null) {
			if (other.enablecompletion != null)
				return false;
		} else if (!enablecompletion.equals(other.enablecompletion))
			return false;
		if (enrolledusercount == null) {
			if (other.enrolledusercount != null)
				return false;
		} else if (!enrolledusercount.equals(other.enrolledusercount))
			return false;
		if (forcetheme == null) {
			if (other.forcetheme != null)
				return false;
		} else if (!forcetheme.equals(other.forcetheme))
			return false;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (fullname == null) {
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname))
			return false;
			if (groupmode == null) {
			if (other.groupmode != null)
				return false;
		} else if (!groupmode.equals(other.groupmode))
			return false;
		if (groupmodeforce == null) {
			if (other.groupmodeforce != null)
				return false;
		} else if (!groupmodeforce.equals(other.groupmodeforce))
			return false;
		if (hiddensections == null) {
			if (other.hiddensections != null)
				return false;
		} else if (!hiddensections.equals(other.hiddensections))
			return false;
		if (lang == null) {
			if (other.lang != null)
				return false;
		} else if (!lang.equals(other.lang))
			return false;
		if (maxbytes == null) {
			if (other.maxbytes != null)
				return false;
		} else if (!maxbytes.equals(other.maxbytes))
			return false;
		if (newsitems == null) {
			if (other.newsitems != null)
				return false;
		} else if (!newsitems.equals(other.newsitems))
			return false;
		if (numsections == null) {
			if (other.numsections != null)
				return false;
		} else if (!numsections.equals(other.numsections))
			return false;
		if (shortname == null) {
			if (other.shortname != null)
				return false;
		} else if (!shortname.equals(other.shortname))
			return false;
		if (showgrades == null) {
			if (other.showgrades != null)
				return false;
		} else if (!showgrades.equals(other.showgrades))
			return false;
		if (showreports == null) {
			if (other.showreports != null)
				return false;
		} else if (!showreports.equals(other.showreports))
			return false;
		if (startdate == null) {
			if (other.startdate != null)
				return false;
		} else if (!startdate.equals(other.startdate))
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
			return false;
		if (summaryformat != other.summaryformat)
			return false;
		if (timecreated == null) {
			if (other.timecreated != null)
				return false;
		} else if (!timecreated.equals(other.timecreated))
			return false;
		if (timemodified == null) {
			if (other.timemodified != null)
				return false;
		} else if (!timemodified.equals(other.timemodified))
			return false;
		if (visible == null) {
			if (other.visible != null)
				return false;
		} else if (!visible.equals(other.visible))
			return false;
		return true;
	}

	/**
	 * Default Comparable
	 */
	public int compareTo(MoodleCourse o) {
		return this.id.intValue() - o.getId().intValue();
	}

}
