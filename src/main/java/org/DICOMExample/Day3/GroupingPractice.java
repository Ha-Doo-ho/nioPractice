package org.DICOMExample.Day3;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 학습 포인트: "단순한 값이 아니라, '계산된 결과'로 묶기"
 * 지금까지는 getExtension이라는 메서드를 따로 만들어서 썼지만, 람다식 내부에서 직접 문자열을 자르고 볶아서 "동적으로 키(Key)를 만들어내는" 연습이 필요합니다.
 *
 * 이번 실습 목표: "가짜 환자 데이터 분류하기"
 * 상황: 파일 이름이 환자ID_날짜.txt 형식으로 되어 있다고 가정합니다. (예: 001_20240101.txt, 001_20240201.txt, 002_20240101.txt)
 *
 * 목표: 뒤의 날짜는 무시하고, 앞의 **"환자ID(001, 002)"**끼리만 파일을 묶어봅니다.
 *                                   string, path
 */

@Slf4j
public class GroupingPractice {
    public static void main(String[] args) {
        Path startPath = Path.of(".");//현재 폴더부터 스캔

        try (Stream<Path> stream = Files.walk(startPath)){ //재귀적으로 파일을 탐색
            Map<String, List<Path>> patientMap = stream.filter(path -> Files.isRegularFile(path)) //목표는 환자 "파일"을 찾는 것임. 폴더를 찾는 것이 아님. 폴더는 isDirectory()
                    .collect(Collectors.groupingBy(path -> { //collect() 스트림을 모음. Collectors.groupingBy() 파일들을 분류함. 즉,끼리끼리 모음.
                        // path->{} 끼리끼리 모으기 위해 분류기준을 선정.
                        String fileName = path.getFileName().toString();

                        if (fileName.contains("_")) {
                            return fileName.split("_")[0];
                        } else {
                            return "Others";
                        }
                    }));
            //Map<String, List<Path>> 이므로 매개변수 두개고 두개 이상부터 괄호로 묶음. (patientId, files) 이렇게 묶어야 함.
            patientMap.forEach((patientId, files)->{
                log.info("===환자 ID: [{}] (총 {}개의 검사 파일) ===", patientId,files.size());
                files.forEach(f->log.info(" ㄴ 파일: {}",f.getFileName()));
            });
        } catch (IOException e) {
            log.error("에러발생", e);
        }


    }
}
