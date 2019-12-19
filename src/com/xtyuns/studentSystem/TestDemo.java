package com.xtyuns.studentSystem;

import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Random;

public class TestDemo {

	static Scanner sc = new Scanner(System.in);
	private static Student[] students = new Student[100];
	static String fileName = "students.txt";

	public static void main(String[] args) throws IOException {
		String index = "";// 不使用整数类型是为了兼容用户的错误输入
		while (!index.equals("0")) {
			showMenu();
			index = sc.next();

			switch (index) {
			case "0":
				System.out.println("再见!");
				break;
			case "1":
				if (students[students.length - 1] != null) {
					System.out.println("学生信息已满,无法继续插入!");
					break;
				}
				addStudent();
				break;
			case "2":
				if (students[0] == null) {
					System.out.println("请先添加学生信息!");
					break;
				}
				sortStudents();
				break;
			case "3":
				if (students[0] == null) {
					System.out.println("请先添加学生信息!");
					break;
				}
				delStudentByNo();
				break;
			case "4":
				if (students[0] == null) {
					System.out.println("请先添加学生信息!");
					break;
				}
				changeStudentInfo();
				break;
			case "5":
				if (students[0] == null) {
					System.out.println("请先添加学生信息!");
					break;
				}
				System.out.print("请输入学生的学号:");
				int sNo = sc.nextInt();
				queryStudentByNo(sNo);
				break;
			case "6":
				if (students[0] == null) {
					System.out.println("请先添加学生信息!");
					break;
				}
				int score = 85;// 指定分数
				System.out.println(">>>各科成绩均大于" + score + "的学生:");
				String info = getStudentsInfoTxt(getStudentsByScore(students, score));
				System.out.println(info);
				break;
			case "7":
				if (students[0] == null) {
					System.out.println("请先添加学生信息!");
					break;
				}
				savaToFile();
				break;
			case "8":
				int num = 10;
				randomData(num);
				System.out.println("已生成" + num + "条数据！");
				break;
			default:
				System.out.println("输入有误,请检查后重试!");
				break;
			}
		}
	}

	// 显示操作菜单
	private static void showMenu() {
		for (int i = 0; i < 30; i++)
			System.out.print("*");
		System.out.println();
		System.out.println("	1.添加学生信息");
		System.out.println("	2.按指定规则进行排序");
		System.out.println("	3.删除指定学号的学生信息");
		System.out.println("	4.修改指定学号的学生信息");
		System.out.println("	5.查询指定学号的学生信息");
		System.out.println("	6.单科成绩均大于85的学生");
		System.out.println("	7.保存所有学生信息");
		System.out.println("	8.生成随机数据");
		System.out.println("	0.退出系统");
		for (int i = 0; i < 30; i++)
			System.out.print("*");
		System.out.println();
		System.out.print("请选择:");
	}

	// 添加学生信息
	private static void addStudent() {
		System.out.println("请按格式输入学生信息:");
		System.out.println("(学号;姓名;数学成绩;语文成绩;计算机成绩)");
		String info = sc.next();
		info = info.replace("；", ";");// 干掉全角
		String[] arr = info.split(";");
		// 数据检验,因为永远不要相信用户传来的数据
		// (其实这里的校验还不够严格,因为当用户输入5个字符串的数据时,在转换整数时就会报错)
		while (arr.length != 5) {
			System.out.println("输入格式错误,请重新输入:");
			info = sc.next();
			arr = info.split(";");
		}
		for (int i = 0; i < students.length; i++) {
			if (students[i] == null) {
				students[i] = new Student(Long.parseLong(arr[0]), arr[1], Integer.parseInt(arr[2]),
						Integer.parseInt(arr[3]), Integer.parseInt(arr[4]));
				break;
			}
		}
		System.out.println("学生信息添加成功!");
	}

	// 将学生进行排序
	private static void sortStudents() {
		System.out.println("请选择排序规则:");
		System.out.println("1.按学号排序");
		System.out.println("2.按总分排序");
		System.out.println("3.按语文成绩排序");
		System.out.println("4.按数学成绩排序");
		System.out.println("5.按计算机成绩排序");
		String index = sc.next();
		// 防止用户输入错误内容
		while (!index.equals("1") && !index.equals("2") && !index.equals("3") && !index.equals("4")
				&& !index.equals("5")) {
			System.out.print("输入有误,请重新输入:");
			index = sc.next();
		}
		switch (index) {
		case "1":
			System.out.println(">>>按学号排序结果:");
			rankByNo(students);
			printStudentInfo(students);
			break;
		case "2":
			System.out.println(">>>按总分排序结果:");
			rankByTotal(students);
			printStudentInfo(students);
			break;
		case "3":
			System.out.println(">>>按语文成绩排序结果:");
			rankBycN(students);
			printStudentInfo(students);
			break;
		case "4":
			System.out.println(">>>按数学成绩排序结果:");
			rankByMath(students);
			printStudentInfo(students);
			break;
		case "5":
			System.out.println(">>>按计算机成绩排序结果:");
			rankByCs(students);
			printStudentInfo(students);
			break;
		}
	}

	// 通过二分折半算法查找指定学号的学生,返回其在students数组中的下标
	private static int BinarySearchStudentByNo(Student[] students, long sNo, int low, int high) {
		if (high == -1) {// 递归时第一次调用本方法的标记
			// 只取有数据的对象参与查找
			for (int i = 0; i < students.length; i++) {
				if (students[i] == null) {
					high = i - 1;
					break;
				}
			}
		}
		int mid = low + (high - low) / 2;
		if (students[mid].getsNo() == sNo)
			return mid;
		if (low >= high)
			return -1;// 数组中不存在要查找的数据
		if (students[mid].getsNo() > sNo)
			return BinarySearchStudentByNo(students, sNo, low, mid - 1);
		if (students[mid].getsNo() < sNo)
			return BinarySearchStudentByNo(students, sNo, mid + 1, high);
		return mid; // 这句好像没什么用,但是不加又会报错0.0...
	}

	// 删除指定学号的学生信息
	private static void delStudentByNo() {
		System.out.print("请输入学生的学号:");
		int sNo = sc.nextInt();
		rankByNo(students);// 使用折半查找前一定要先进行排序!!!
		int n = BinarySearchStudentByNo(students, sNo, 0, -1);// -1作为特殊标记,方法内再次处理该变量

		if (n == -1) {
			System.out.println("你还未录入该学生的信息!");
		} else {
			for (int i = n; i < students.length; i++) {
				if (students[i] == null)
					break;
				students[i] = students[i + 1];
			}
			System.out.println("数据删除成功!");
		}
	}

	// 修改指定学号的学生信息(可以通过addStudent方法优化本方法,但是我不想改了0.0)
	private static void changeStudentInfo() {
		System.out.print("请输入学生的学号:");
		int sNo = sc.nextInt(), index;
		rankByNo(students);// 使用折半查找前一定要先进行排序!!!
		index = BinarySearchStudentByNo(students, sNo, 0, -1);
		if (index == -1) {
			System.out.println("你还未录入该学生的信息!");
		} else {
			System.out.println("请按格式输入学生信息:");
			System.out.println("(学号;姓名;数学成绩;语文成绩;计算机成绩)");
			String info = sc.next();
			String[] arr = info.split(";");
			while (arr.length != 5) {
				System.out.println("输入格式错误,请重新输入:");
				info = sc.next();
				arr = info.split(";");
			}
			students[index].setsNo(Long.parseLong(arr[0]));
			students[index].setsName(arr[1]);
			students[index].setMathScore(Integer.parseInt(arr[2]));
			students[index].setcNScore(Integer.parseInt(arr[3]));
			students[index].setCsScore(Integer.parseInt(arr[4]));
			students[index]
					.setTotalScore(Integer.parseInt(arr[2]) + Integer.parseInt(arr[3]) + Integer.parseInt(arr[4]));
			System.out.println(">>>数据修改成功:");
			queryStudentByNo(Long.parseLong(arr[0]));
		}

	}

	// 查询指定学号的学生信息
	private static void queryStudentByNo(long sNo) {
		rankByNo(students);// 使用折半查找前一定要先进行排序!!!
		int index = BinarySearchStudentByNo(students, sNo, 0, -1);
		if (index == -1) {
			System.out.println("未查询到该学生的信息!");
		} else {
			Student[] s = new Student[1];
			s[0] = students[index];
			printStudentInfo(s);
		}
	}

	// 获取文本格式的学生信息(本方法可以便于将学生信息输出至文本文件)
	private static String getStudentsInfoTxt(Student[] students) {
		String txt = "学号\t姓名\t数学\t语文\t计算机\t总分";
		for (Student stu : students)
			if (stu != null)
				txt += "\r\n" + stu.toString();
		return txt;
	}

	// 输出学生信息
	private static void printStudentInfo(Student[] students) {
		System.out.println(getStudentsInfoTxt(students));
	}

	// 使用冒泡法将学生按照学号进行升序排序
	private static void rankByNo(Student[] students) {
		for (int i = 0; i < students.length - 1; i++) {
			for (int j = 0; j < students.length - i - 1; j++) {
				if (students[j + 1] == null)
					break;// 不处理空对象
				if (students[j].getsNo() > students[j + 1].getsNo()) {
					Student t = students[j];
					students[j] = students[j + 1];
					students[j + 1] = t;
				}
			}
		}
		// 引用型参数,无需返回值
	}

	// 将学生按照总分进行降序排序
	private static void rankByTotal(Student[] students) {
		for (int i = 0; i < students.length - 1; i++) {
			for (int j = 0; j < students.length - i - 1; j++) {
				if (students[j + 1] == null)
					break;// 不处理空对象
				if (students[j].getTotalScore() < students[j + 1].getTotalScore()) {
					Student t = students[j];
					students[j] = students[j + 1];
					students[j + 1] = t;
				}
			}
		}
		// 引用型参数,无需返回值
	}

	// 将学生按照语文成绩排序
	private static void rankBycN(Student[] students) {
		for (int i = 0; i < students.length - 1; i++) {
			for (int j = 0; j < students.length - i - 1; j++) {
				if (students[j + 1] == null)
					break;// 不处理空对象
				if (students[j].getcNScore() < students[j + 1].getcNScore()) {
					Student t = students[j];
					students[j] = students[j + 1];
					students[j + 1] = t;
				}
			}
		}
		// 引用型参数,无需返回值
	}

	// 将学生按照数学成绩排序
	private static void rankByMath(Student[] students) {
		for (int i = 0; i < students.length - 1; i++) {
			for (int j = 0; j < students.length - i - 1; j++) {
				if (students[j + 1] == null)
					break;// 不处理空对象
				if (students[j].getMathScore() < students[j + 1].getMathScore()) {
					Student t = students[j];
					students[j] = students[j + 1];
					students[j + 1] = t;
				}
			}
		}
		// 引用型参数,无需返回值
	}

	// 将学生按照计算机成绩排序
	private static void rankByCs(Student[] students) {
		for (int i = 0; i < students.length - 1; i++) {
			for (int j = 0; j < students.length - i - 1; j++) {
				if (students[j + 1] == null)
					break;// 不处理空对象
				if (students[j].getCsScore() < students[j + 1].getCsScore()) {
					Student t = students[j];
					students[j] = students[j + 1];
					students[j + 1] = t;
				}
			}
		}
		// 引用型参数,无需返回值
	}

	// 返回所有成绩均大于指定分数的学生
	private static Student[] getStudentsByScore(Student[] students, int score) {
		Student[] s = new Student[students.length];
		for (Student stu : students) {
			if (stu != null && stu.getMathScore() > score && stu.getcNScore() > score && stu.getCsScore() > score) {
				for (int i = 0; i < s.length; i++) {
					if (s[i] == null) {
						s[i] = stu;
						break;
					}
				}
			}
		}
		rankByTotal(s);
		return s;
	}

	// 保存数据
	private static void savaToFile() throws IOException {
		File fl = new File(fileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(fl));
		if (!fl.exists()) {
			fl.createNewFile();
		}
		rankByNo(students);
		String txt = getStudentsInfoTxt(students);
		bw.write(txt);
		bw.close();
		System.out.println("数据已成功保存到-> " + fl.getAbsolutePath());
	}

	// 生成指定数量的学生数据
	private static void randomData(int num) {
		if (num > students.length)// 防止数组下标溢出
			num = students.length;
		Random rand = new Random();
		students = new Student[students.length];// 清空数组原有内容
		for (int i = 0; i < num; i++) {
			int m, n, k;
			if (i % 2 == 0) {// 生成一部分各科成绩都大于85的学生
				m = rand.nextInt(15) + 85;
				n = rand.nextInt(15) + 85;
				k = rand.nextInt(15) + 85;
			} else {
				m = rand.nextInt(100);
				n = rand.nextInt(100);
				k = rand.nextInt(100);
			}
			students[i] = new Student(1001 + i, "s_" + i, m, n, k);
		}
	}
}
