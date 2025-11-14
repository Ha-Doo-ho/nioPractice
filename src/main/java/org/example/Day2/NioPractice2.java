package org.example.Day2;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Slf4j
public class NioPractice2 {
    public static void main(String[] args) {
        Path startpath = Path.of("./src"); //시작 위치  D:\NioPractice\nioPractice\src

        log.info("파일 탐색을 시작합니다. 탐색시작 파일:{}",startpath);
        try(Stream<Path> stream = Files.walk(startpath)) { //시작연산 -> 시작 위치부터 파일을 탐색하기 시작함.
            stream.filter(path -> Files.isRegularFile(path)) //중간연산. 파일과 폴더를 구별. 이게 정말 파일이냐고 물음.
                    .map(path -> path.getFileName().toString()) //중간연산. path 객체에 저장된 파일 이름을 String 문자열 객체로 변환
                    .sorted()

                    .forEach(name->{  //최종연산: 이제 path가 아닌 String으로 형변환된 name이 넘어옴.
                        log.info("발견된 파일 이름: {}",name);
                        log.info("파일 처리중... 처리중인 파일:{}",name);
                    });
        }catch (Exception e) {
          e.printStackTrace();
        }




    }
}
