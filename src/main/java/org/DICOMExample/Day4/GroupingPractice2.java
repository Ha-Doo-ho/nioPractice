package org.DICOMExample.Day4;

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
public class GroupingPractice2 {
    public static void main(String[] args) {
        Path startPath = Paths.get(".");

        try (Stream<Path> stream = Files.walk(startPath)){ //시작 지점부터 재귀적으로 탐색
            Map<String, List<Path>> patientMap = stream
                    .filter(path -> Files.isRegularFile(path)) //파일 아닌 것은 전부 걸러야 함.
                    .collect(Collectors.groupingBy(path -> {
                        String fileName = path.getFileName().toString();

                        try {   //Files.size(스트림의 데이터 변수(제네릭 변수))는 IOException 처리를 요구함.
                            if (Files.size(path) > 1024) {  //size가 1KB(1024 byte) 보다 크면 BigFile, 작으면 SmallFile로 묶기
                                if(fileName.contains("_")) {
                                    return fileName.split("_")[0]; // _로 분류하고 _앞을 인덱스 0으로 보고 분류한다.
                                }
                                return "BigFile";
                            } else{
                                return "SmallFile";
                            }
                        }catch (IOException e) {
                            return "Error";
                        }
                    }));
            patientMap.forEach((patientId,files)->{
                log.info("===환자 ID: [{}] (총 {}개의 검사 파일)===",patientId,files.size());
                files.forEach(f->log.info("파일 : {}",f.getFileName()));
            });

        }catch (IOException e) {
            log.error("error발생",e);
        }


    }
}

/*
미션: groupingBy의 기준을 바꿔서, "파일 크기가 1KB(1024 byte)보다 크면 'BigFile', 작으면 'SmallFile'로 묶기"
 */