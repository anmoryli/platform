package com.anmory.platform.DatabaseService.Mapper;

import com.anmory.platform.DatabaseService.Dao.Literature;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description TODO
 * @date 2025-04-23 下午11:21
 */

@Mapper
public interface LiteratureMapper {
    @Insert("INSERT INTO literature (" +
            "title, tibetan_title, authors, publication_year, journal_or_publisher, " +
            "volume_issue, pages, abstract, download_link, file_path, main_plant, " +
            "research_field, keywords,recorded_by" +
            ") VALUES (" +
            "#{title}, #{tibetanTitle}, #{authors}, #{publicationYear}, #{journalOrPublisher}, " +
            "#{volumeIssue}, #{pages}, #{abstractText}, #{downloadLink}, #{filePath}, #{mainPlant}, " +
            "#{researchField}, #{keywords},#{recordedBy}" +
            ")")
    int insertLiterature(
            @Param("title") String title,
            @Param("tibetanTitle") String tibetanTitle,
            @Param("authors") String authors,
            @Param("publicationYear") Integer publicationYear,
            @Param("journalOrPublisher") String journalOrPublisher,
            @Param("volumeIssue") String volumeIssue,
            @Param("pages") String pages,
            @Param("abstractText") String abstractText,
            @Param("downloadLink") String downloadLink,
            @Param("filePath") String filePath,
            @Param("mainPlant") String mainPlant,
            @Param("researchField") String researchField,
            @Param("keywords") String keywords,
            @Param("recordedBy") String recordedBy
    );

    @Select("select * from literature")
    List<Literature> selectAll();

    @Delete("delete from literature where literature_id=#{id}")
    int deleteLiterature(int id);

    @Select("select * from literature order by literature.literature_id desc limit #{offset}, #{size}")
    List<Literature> selectLiteraturePage(int offset, int size);

    @Select("select * from literature where literature.title = #{title} limit 1")
    Literature getLiteratureByTitle(String title);

    @Select("select * from literature where literature.literature_id = #{id} limit 1")
    Literature selectLiteratureById(int id);
}
