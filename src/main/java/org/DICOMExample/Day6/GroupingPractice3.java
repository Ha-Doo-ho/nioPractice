package org.DICOMExample.Day6;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class GroupingPractice3 {
    public static void main(String[] args) {
        Path startPath = Paths.get("E:/Converted_T1_AXIAL_to_PNG");

        if(!Files.exists(startPath)){
            log.error("경로가 없습니다.");
            return;
        }

        try (Stream<Path> stream = Files.walk(startPath)){
            Map<String, List<Path>> PatientMap = stream.filter(path -> Files.isRegularFile(path)) //파일만 필터링. (기본)
                    .collect(Collectors.groupingBy(
                            path -> {
                                String fileName = path.getFileName().toString(); //분류 기준을 조작.

                                if (fileName.contains("_")) {
                                   return fileName.split("_")[0];
                                }
                                else{
                                    return "Others";
                                }
                            }));

            PatientMap.forEach((patientId, files) -> {
                log.info("=== 환자 ID: [{}] (총 {}개의 검사 파일) ===", patientId, files.size());
                files.forEach(f->log.info("ㄴ 파일: {}",f.getFileName()));
            });
        } catch (IOException e) {
            log.error("에러발생",e);
        }

    }
}
