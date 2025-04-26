package com.anmory.platform.DatabaseService.Service;

import com.anmory.platform.DatabaseService.Dao.Literature;
import com.anmory.platform.DatabaseService.Mapper.LiteratureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-23 下午11:22
 */

@Service
public class LiteratureService {
    @Autowired
    LiteratureMapper LiteratureMapper;

    public int insertLiterature(
            String title,
            String tibetanTitle,
            String authors,
            Integer publicationYear,
            String journalOrPublisher,
            String volumeIssue,
            String pages,
            String abstractText,
            String downloadLink,
            String filePath,
            String mainPlant,
            String researchField,
            String keywords,
            String recordedBy
    ) {
        return LiteratureMapper.insertLiterature(
                title,
                tibetanTitle,
                authors,
                publicationYear,
                journalOrPublisher,
                volumeIssue,
                pages,
                abstractText,
                downloadLink,
                filePath,
                mainPlant,
                researchField,
                keywords,
                recordedBy
        );
    }

    public Literature getLiteratureByTitle(String title) {
        return LiteratureMapper.getLiteratureByTitle(title);
    }

    public List<Literature> selectAll() {
        return LiteratureMapper.selectAll();
    }

    public int deleteLiterature(int id) {
        return LiteratureMapper.deleteLiterature(id);
    }

    public List<Literature> selectLiteraturePage(int offset, int size) {
        return LiteratureMapper.selectLiteraturePage(offset, size);
    }
}
