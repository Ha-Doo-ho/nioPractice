package org.example.Day2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class NioPractice3 {

    //보조 메서드: 파일 경로에서 확장자만 뽑아냄.
    private static String getExtension(Path path) {
        //파일을 받음
        //맨 뒤에 것만 남기고 싹다 버림. 맨 뒤에 것만 추출하는 함수를 사용
        String fileName = path.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf("."); // 확장자는 .뒤로 시작하므로 .의 위치를 반환함. 거기서부터 substring 반환하면 확장자만 나옴.

        if(lastDotIndex == -1 || lastDotIndex == 0) { //점(.)이 없거나 맨 앞에 있으면 "확장자없음" 처리
            return "noExtension";
        }
        return fileName.substring(lastDotIndex + 1); // .뒤부터 확장자 이므로, lastDotIndex + 1을 함.
    }



    public static void main(String[] args) {
        log.info("파일 그룹화 연습 시작...");

        Path startPath = Path.of("./src"); // .붙으면 현재경로 즉 src부터 시작하며, ..은 부모경로 즉 src의 위 경로부터 탐색을 시작함.

        boolean exists = Files.exists(startPath);
        try {
            if(!exists) {
                log.error("해당 경로({})는 존재하지 않습니다.",startPath.toAbsolutePath()); //
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try(Stream<Path> stream = Files.walk(startPath)) {
            //최종 연산의 결과물: Key:확장자(String), Value:파일목록(List) 인 Map

            Map<String, List<Path>> groupedMap = stream.filter(path -> Files.isRegularFile(path))
                    .collect(Collectors.groupingBy(path -> getExtension(path)));


            groupedMap.forEach((String extension, List<Path> fileList)->{// String, List<Path>적지 않아도 타입 추론으로 알아낸다.
                log.info("=== 확장자:[.{}] (총 {}개) ===",extension,fileList.size());
            });
        } catch (IOException e){
            log.error("파일 탐색 중 심각한 오류!", e);
        }



    }
}
