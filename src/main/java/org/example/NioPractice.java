package org.example;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
public class NioPractice {
    public static void main(String[] args) {
        //Path: 인터페이스. 변수 타입
        //Paths: Path객체를 쉽게 만들기 위한 도구함. 함수의 모음집.
        //자바 11부터는 Path myPath = Path.of("C:\\Windows"); Paths도구함을 거치지 않고서도 Path인터페이스가 직접 자신을 생성할 수 있게 됨.
        //Path path = Paths.get("C:\\Windows"); ->전통적인 방법

        log.info("프로그램을 시작합니다...");
        Path startDir = Path.of("./src"); // .(점 하나):현재 내가 있는 폴더
                                              //  ..(점 둘): 내 바로 위의 부모 폴더
        log.info("탐색 시작 경로:{}", startDir.toAbsolutePath()); //절대경로를 제작. startDir은 /src인데 여기서 D:\NioPractice\nioPractice\src 이렇게 만듦.

        try (Stream<Path> stream = Files.walk(startDir)) { //탐색의 시작. 핵심 중의 핵심함수로 이게 없으면 재귀함수 다 짜야 함. Files.walk(시작폴더): 폴더 파일 가리지 않고 다 가져옴.
            stream
                    .filter(path -> Files.isRegularFile(path)) //이게 '진짜 파일(File)'이니? 아니면 '폴더(Directory)'니?" 라고 물어보는 함수입니다. 이 함수는 순수한 파일(텍스트, 이미지 등) 만 통과시킴.
                                                                   // 반대로 폴더만 뽑아내는 함수는 Files.isDirectory()이다.
                    .forEach(path -> {  //람다식(->)은 "데이터가 하나 들어왔을 때, 이걸 path라고 부를게. 그리고 나서..." 라고 정의하는 것입니다. 즉 path가 아닌 p라고 지어도 상관 없음.
                        log.debug("파일 발견: {}", path.getFileName());
                        log.info("파일 처리중: {}", path.getFileName());
                    });
        } catch (IOException e) {
            log.error("파일 탐색 중 오류 발생!", e);
        }
        log.info("작업 완료");


//
//        log.info("프로그램 시작......");
//        Path startpath = Paths.get("./Converted_T1_AXIAL_to_PNG"); //   D:\NioPractice\nioPractice\Converted_T1_AXIAL_to_PNG 부터 탐색 시작.
//        log.info("{}폴더 부터 탐색을 시작합니다.", startpath.toAbsolutePath());
//
//        try(Stream<Path> stream = Files.walk(startpath)) { //폴더와 파일 가리지 않고 다 찾음. 그러면 이게 폴더인지 파일인지를 가려내는 함수를 사용하면 됨. 파일만 뽑아 내야 함.
//            stream.filter(path -> Files.isRegularFile(path)) //한줄이니까 {} 없어도 됨.
//                    .forEach(path -> {
//                        log.info("파일 발견: {}",path.getFileName());   //여기는 2줄임. {} 필요함.
//                        log.info("파일 처리 중: {}",path.getFileName());
//                    });
//        } catch (IOException e){
//            e.printStackTrace(); //디버그 용도.
//        }
//        log.info("작업 완료");
    }
}
