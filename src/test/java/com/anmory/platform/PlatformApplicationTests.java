package com.anmory.platform;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.system.SystemUtil;
import com.anmory.platform.GraphService.Dao.Herb;
import com.anmory.platform.GraphService.Repository.HerbRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlatformApplicationTests {

	@Autowired
	HerbRepo herbRepo;
	@Test
	void testFindHerbByName() {
		Herb byName = herbRepo.findByName("雪莲花");
		System.out.println(byName);
	}

	@Test
	void captcha() {
		//定义图形验证码的长和宽
		LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

//图形验证码写出，可以写出到文件，也可以写出到流
		lineCaptcha.write("D:\\Code\\Project\\SpringBoot\\Platform\\src\\test\\java\\com\\anmory\\platform\\line.png");
//输出code
		Console.log(lineCaptcha.getCode());
//验证图形验证码的有效性，返回boolean值
		System.out.println(lineCaptcha.verify("1234"));

		System.out.println(SystemUtil.getUserInfo());

////重新生成验证码
//		lineCaptcha.createCode();
//		lineCaptcha.write("d:/line.png");
////新的验证码
//		Console.log(lineCaptcha.getCode());
////验证图形验证码的有效性，返回boolean值
//		lineCaptcha.verify("1234");
	}

	@Test
	void concurrency() {
		ConcurrencyTester tester = ThreadUtil.concurrencyTest(100, () -> {
			// 测试的逻辑内容
			long delay = RandomUtil.randomLong(100, 10000);
			ThreadUtil.sleep(delay);
			Console.log("{} test finished, delay: {}", Thread.currentThread().getName(), delay);
		});

// 获取总的执行时间，单位毫秒
		Console.log(tester.getInterval());
	}

}
