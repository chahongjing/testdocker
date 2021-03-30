
``` shell
# docker安装成功后测试
docker run hello-world
# 构建镜像，进入到docker文件目录（springboot的jar包也在此目录）
docker build -t myapp:1.0 .
# 查看镜像
docker images
# 运行镜像，第一个端口是内部端口，第二个是外部访问端口
docker run --name=myspringboot -d -p 12345:8080 myapp:1.0
# 通过 http://localhost:12345/index 访问
docker ps
-- 控制台查看日志
docker logs -f --tail=100 ca     # ca是containerid
-- 查看日志文件先要进到容器内部

https://blog.csdn.net/duangecho/article/details/80315316

mvn package -Dmaven.test.skip=true docker:build
```