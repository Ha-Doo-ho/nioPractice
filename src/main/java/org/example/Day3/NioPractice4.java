package org.example.Day3;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class NioPractice4 {
    public static void main(String[] args) {
        log.info("파일 그룹화 연습 시작...");

        Path startPath = Path.of("./src"); //사작위치: D:\NioPractice\nioPractice\src
        //시작 위치를 정한다. 만약 시작 위치가 이상하면 컷.

        if(!Files.exists(startPath)) { //파일, 디렉터리, 심볼릭 링크 등 이런 것들이 시작경로에 없으면
            log.error("경로가 없습니다");
            return;
        }

        try(Stream<Path> stream = Files.walk(startPath)) { //탐색을 재귀으로 시작.
            Map<String, List<Path>> groupedMap = stream.filter(path -> Files.isRegularFile(path)) //결국 필터링 해야 할 것은 파일임. DICOM파일을 찾는 것이 우리의 목적임.
                    //스트림에는 [src(폴더), main(폴더), java(폴더), NioPractice.java(파일), test.txt(파일)]로 흘러들어옴.
                    .collect(Collectors.groupingBy(path -> getExtension(path)));//스트림 파이프라인 끝

            /** 개념: 분류하기
             *       더미를 분류기준에 맞게 통에 담는다. 이 개념에 필요한 3가지를 생각해야 함.
             *[더미:stream(데이터의 흐름) / 분류 기준:Classifier / 이게 무슨 종류지? Map(통)]
             */

            /**
             * .collect():모으기. 데이터의 흐름(스트림) 즉, 더미를 최종적으로 모음.
             * Collectors.groupingBy() 분류해서 그냥 모으지 말고, 끼리끼리 뭉쳐서 모으기.
             * path -> getExtension() 끼리끼리 뭉쳐서 모으는데, 그 기준이 path -> getExtension(path).
             */



            //결과 확인. 맵을 순회하며 출력
        groupedMap.forEach((extension,fileList)->{
            log.info("===확장자: [.{}](총 {}개) ===", extension, fileList.size());
            fileList.forEach(file->log.info("-{}",file.getFileName()));
        });
        }catch (IOException e) {
            log.error("파일 탐색 중 심각한 오류!",e);
        }

    }

    private static String getExtension(Path path) {
        String fileName = path.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf("."); //.의 위치를 반환함. 거기서부터 substring 반환하면 확장자만 나옴.

        if(lastDotIndex == - 1 || lastDotIndex == 0) {
            return "NoExtension";
        }
        return fileName.substring(lastDotIndex + 1); //.있는 인덱스 다음의 인덱스들을 반환하면 됨. .dcm이면 . 다음인 dcm만 반환.
    }
}
