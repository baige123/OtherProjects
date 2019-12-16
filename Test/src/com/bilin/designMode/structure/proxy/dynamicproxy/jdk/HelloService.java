package com.bilin.designMode.structure.proxy.dynamicproxy.jdk;

//委托类
public class HelloService implements IHelloService {

 @Override
 public String sayHello(String userName) {
     System.out.println(userName + " hello");
     return userName + " hello";
 }

 @Override
 public String sayByeBye(String userName) {
     System.out.println(userName + " ByeBye");
     return userName + " ByeBye";
 }
}
