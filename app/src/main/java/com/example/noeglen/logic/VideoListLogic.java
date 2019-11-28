package com.example.noeglen.logic;

import com.example.noeglen.data.VideoDAO;
import com.example.noeglen.data.VideoDTO;

import java.util.List;

public class VideoListLogic {

    public List<String> getWeekList(){
        List<String> weekList;
        VideoDAO videoDAO = new VideoDAO();
        weekList = videoDAO.getWeekList();
        return weekList;
    }

    public List<VideoDTO> getAllVideosFromWeek(String week){
        List<VideoDTO> videoList;
        VideoDAO videoDAO = new VideoDAO();
        videoList = videoDAO.getAllVideosFromWeek(week);
        return videoList;
    }

    public VideoDTO getVideo(String week, String videoName){
        VideoDTO video;
        VideoDAO videoDAO = new VideoDAO();
        video = videoDAO.getVideo(week, videoName);
        return  video;
    }


}
