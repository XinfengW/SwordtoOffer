package cn.edu.tju.rico.inttree;

import java.util.LinkedList;



/**
 * Title: 二叉树(非线性结构)的构建及相关操作 
 * Description:
 * 以广义表形式的字符串构建二叉树：'()'前表示根结点，括号中左右子树用逗号隔开，逗号不能省略 二叉树的层次/广序遍历算法
 * 二叉树的前序、中序、后序遍历的递归和非递归算法(对每个节点而言，三种遍历方式都需要遍历该结点三次，三者唯一区别在于该结点的访问时机)
 * 根据二叉树的前序、中序或中序、后序遍历结果构建二叉树 二叉树的高度 二叉树的结点总数 根据树的根结点复制一颗二叉树 获取二叉树的根结点，孩子节点
 * 打印二叉树 判断两颗二叉树是否相等
 * 
 * @author rico
 * @created 2017年5月23日 上午11:16:12
 */
public class BinaryTree {

	/** 二叉树的根结点 (@author: rico) */
	private TreeNode root;

	/**
	 * 无参构造函数
	 * 
	 * @description 默认无参构造函数
	 * @author rico
	 * @created 2017年5月24日 下午3:36:35
	 */
	public BinaryTree() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @description 根据一个树的根结点复制构造树
	 * @author rico
	 * @created 2017年5月23日 下午2:17:06
	 * @param TreeNode
	 *            原树的根结点
	 */
	public BinaryTree(TreeNode TreeNode) {
		// TODO Auto-generated constructor stub
		this.root = copy(TreeNode);
	}

	/**
	 * 构造函数
	 * 
	 * @description 根据一个树的前序遍历结果复制构造树
	 * @author rico
	 * @created 2017年5月24日 下午3:38:02
	 * @param preOrderStr
	 */
	public BinaryTree(char[] preOrderStr) {
		root = createTreeByPreOrederStr(preOrderStr, null);
	}

	/**
	 * 构造函数
	 * 
	 * @description 根据一个树的前序遍历+中序遍历(或中序遍历+后序遍历)复制构造树
	 * @author rico
	 * @created 2017年5月24日 下午3:38:33
	 * @param s1
	 * @param s2
	 * @param isPreIn
	 */
	public BinaryTree(String s1, String s2, boolean isPreIn) {
		if (isPreIn) {
			root = createBinaryTreeByPreAndIn(s1, s2);
		} else {
			root = createBinaryTreeByInAndPost(s1, s2);
		}
	}

	/**
	 * @description 根据广义表表达式创建树
	 * @author rico
	 * @created 2017年5月22日 下午3:16:01
	 * @param exp
	 *            广义表
	 */
	public void createBinaryTree(String exp) {
		LinkedList<TreeNode> stack = new LinkedList<TreeNode>(); // 辅助栈
		TreeNode TreeNode = null; // 新结点
		TreeNode temp = null; // 用于入栈
		TreeNode parent = null; // 父亲结点
		boolean flag = false; // true 表示链入到父结点的左孩子位置，false表示链入父结点的右孩子位置

		for (int i = 0; i < exp.length(); i++) { // 逐个读入表达式的各个字符
			char c = exp.charAt(i);
			StringBuilder sb = new StringBuilder();
			if (48 <= c && c <= 57) {
				sb.append(c);
				while (i + 1 < exp.length()) {
					char tmp = exp.charAt(i+1);
					if (48 <= tmp && tmp <= 57) {
						sb.append(exp.charAt(i+1));
						i++;
					}else{
						break;
					}
				}
			}
			switch (c) {
			case '(': // 当前节点有孩子节点，入栈以便设置其孩子
				stack.push(temp);
				flag = true;
				break;
			case ')': // 设置好了栈顶节点的孩子，出栈
				stack.pop();
				break;
			case ',': // 当前节点无孩子，不需要设置其孩子节点，因此不需要入栈
				flag = false;
				break;
			default: // 创建根据内容创建节点
				TreeNode = new TreeNode(Integer.valueOf(sb.toString()));
				break;
			}

			// 若树不存在，则创建树的根结点
			if (root == null) {
				root = TreeNode;
			}

			// 为栈顶节点链入子女
			if (!stack.isEmpty()) {
				if (TreeNode != null) { // 当读入的是'('、')'、','字符时，略过
					parent = stack.peek();
					if (flag) {
						parent.left = TreeNode;
					} else {
						parent.right = TreeNode;
					}
				}
			}

			temp = TreeNode; // 用于入栈
			TreeNode = null; // TreeNode链入后，置空
		}
	}

	/**
	 * @description 根据广义表表达式创建树
	 * @author rico
	 * @created 2017年5月22日 下午3:16:01
	 * @param exp
	 *            广义表
	 */
	public static TreeNode createBinaryTree(String exp, TreeNode root) {
		LinkedList<TreeNode> stack = new LinkedList<TreeNode>(); // 辅助栈
		TreeNode TreeNode = null; // 新结点
		TreeNode temp = null; // 用于入栈
		TreeNode parent = null; // 父亲结点
		boolean flag = false; // true 表示链入到父结点的左孩子位置，false表示链入父结点的右孩子位置

		for (int i = 0; i < exp.length(); i++) { // 逐个读入表达式的各个字符
			char c = exp.charAt(i);
			switch (c) {
			case '(': // 当前节点有孩子节点，入栈以便设置其孩子
				stack.push(temp);
				flag = true;
				break;
			case ')': // 设置好了栈顶节点的孩子，出栈
				stack.pop();
				break;
			case ',': // 当前节点无孩子，不需要设置其孩子节点，因此不需要入栈
				flag = false;
				break;
			default: // 创建根据内容创建节点
				int data = c - 48;
				TreeNode = new TreeNode(data);
				break;
			}

			if (root == null) {
				root = TreeNode;
			}

			// 为栈顶节点链入子女
			if (!stack.isEmpty()) {
				if (TreeNode != null) { // 当读入的是'('、')'、','字符时，略过
					parent = stack.peek();
					if (flag) {
						parent.left = TreeNode;
					} else {
						parent.right = TreeNode;
					}
				}
			}

			temp = TreeNode; // 用于入栈
			TreeNode = null; // TreeNode链入后，置空
		}
		return root;
	}

	/**
	 * @description 广序/层次遍历，工作队列
	 * @author rico
	 * @created 2017年5月22日 下午3:05:57
	 * @return
	 */
	public String levelOrder() {
		StringBuilder sb = new StringBuilder();
		LinkedList<TreeNode> queue = new LinkedList<TreeNode>(); // 辅助队列
		if (root != null) {
			queue.add(root);
			while (!queue.isEmpty()) {
				TreeNode temp = queue.pop();
				sb.append(temp.val).append(" ");

				// 在遍历当前节点时，同时将其左右孩子入队
				if (temp.left != null)
					queue.add(temp.left);
				if (temp.right != null)
					queue.add(temp.right);
			}
		}
		return sb.toString().trim();
	}

	/**
	 * @description 前序遍历(递归)
	 * @author rico
	 * @created 2017年5月22日 下午3:06:11
	 * @param root
	 * @return
	 */
	public String preOrder(TreeNode root) {
		StringBuilder sb = new StringBuilder(); // 存到递归调用栈
		if (root != null) { // 递归终止条件
			sb.append(root.val + " "); // 前序遍历当前结点
			sb.append(preOrder(root.left)); // 前序遍历左子树
			sb.append(preOrder(root.right)); // 前序遍历右子树
		}
		return sb.toString();
	}

	/**
	 * @description 前序遍历(迭代):非线性结构(树)，工作栈：当前节点入栈
	 * @author rico
	 * @created 2017年5月24日 上午8:48:09
	 * @return
	 */
	public String preOrder() {

		StringBuilder sb = new StringBuilder();
		LinkedList<TreeNode> stack = new LinkedList<TreeNode>(); // 工作栈：记录回退路径
		TreeNode TreeNode = root;

		while (TreeNode != null || !stack.isEmpty()) { // 迭代条件
			if (TreeNode != null) { // 当前节点不为空
				sb.append(TreeNode.val + " "); // 访问当前节点
				stack.push(TreeNode); // 当前节点入栈
				TreeNode = TreeNode.left; // 遍历其左子树
			} else {
				TreeNode = stack.pop(); // 弹出其父节点
				TreeNode = TreeNode.right; // 遍历其右子树
			}
		}
		return sb.toString();
	}

	/**
	 * @description 中序遍历(递归)
	 * @author rico
	 * @created 2017年5月22日 下午3:06:28
	 * @param root
	 * @return
	 */
	public String inOrder(TreeNode root) {
		StringBuilder sb = new StringBuilder(); // 存到递归调用栈
		if (root != null) { // 递归终止条件
			sb.append(inOrder(root.left)); // 中序遍历左子树
			sb.append(root.val + " "); // 中序遍历当前结点
			sb.append(inOrder(root.right)); // 中序遍历右子树
		}
		return sb.toString();
	}

	/**
	 * @description 中序遍历(迭代)：非线性结构(树)，工作栈：当前节点入栈
	 * 
	 * @author rico
	 * @created 2017年5月24日 上午9:22:31
	 * @return
	 */
	public String inOrder() {
		StringBuilder sb = new StringBuilder();
		LinkedList<TreeNode> stack = new LinkedList<TreeNode>(); // 工作栈：记录回退路径
		TreeNode TreeNode = root;

		while (TreeNode != null || !stack.isEmpty()) { // 迭代条件
			if (TreeNode != null) { // 当前节点不为空
				stack.push(TreeNode); // 当前节点入栈
				TreeNode = TreeNode.left; // 遍历其左子树
			} else {
				TreeNode = stack.pop(); // 父节点弹栈
				sb.append(TreeNode.val + " "); // 访问父节点
				TreeNode = TreeNode.right; // 遍历访问其右子树
			}
		}
		return sb.toString();
	}

	/**
	 * @description 后序遍历(递归)
	 * @author rico
	 * @created 2017年5月22日 下午3:06:44
	 * @param root
	 * @return
	 */
	public String postOrder(TreeNode root) {
		StringBuilder sb = new StringBuilder(); // 存到递归调用栈
		if (root != null) { // 递归终止条件
			sb.append(postOrder(root.left)); // 后序遍历左子树
			sb.append(postOrder(root.right)); // 后序遍历右子树
			sb.append(root.val + " "); // 后序遍历当前结点
		}
		return sb.toString();
	}

	/**
	 * @description 后序遍历(迭代):非线性结构(树)，工作栈：当前节点入栈
	 *              第三次遍历一个节点时才访问,因此需要在节点TreeNode中新增一个bool字段，用于标记是否需要在本次访问该节点
	 * @author rico
	 * @created 2017年5月24日 上午9:34:48
	 * @return
	 */
	public String postOrder() {
		StringBuilder sb = new StringBuilder();
		LinkedList<TreeNode> stack = new LinkedList<TreeNode>(); // 记录回退路径的工作栈
		TreeNode TreeNode = root;
		while (TreeNode != null || !stack.isEmpty()) { // 迭代条件
			if (TreeNode != null) { // 当前节点不为空
				TreeNode.flag = true; // 首次访问该节点，记为true
				stack.push(TreeNode); // 压栈操作
				TreeNode = TreeNode.left; // 继续遍历左子树
			} else { // 当前节点为空但工作栈不为空
				TreeNode = stack.pop(); // 当前节点弹栈
				if (TreeNode.flag) {
					TreeNode.flag = false; // 第二次访问该节点,改为false
					stack.push(TreeNode); // 只有在第三次才访问，因此，前节点再次压栈
					TreeNode = TreeNode.right; // 访问该节点的右子树
				} else { // 第三次访问该节点
					sb.append(TreeNode.val + " "); // 访问
					TreeNode = null; // 当前节点的左子树、右子树及本身均已访问,需要访问工作栈中的节点
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @description 根据前序、中序遍历结果重建二叉树
	 * @author rico
	 * @created 2017年5月24日 下午12:24:41
	 * @return
	 */
	public TreeNode createBinaryTreeByPreAndIn(String pre, String in) {
		if (pre.length() > 0) {
			TreeNode root = new TreeNode(pre.charAt(0) - 48);
			int index = in.indexOf(pre.charAt(0));
			root.left = createBinaryTreeByPreAndIn(pre.substring(1, index + 1),
					in.substring(0, index));
			root.right = createBinaryTreeByPreAndIn(
					pre.substring(index + 1, pre.length()),
					in.substring(index + 1, in.length()));
			return root;
		}
		return null;
	}

	/**
	 * @description 根据中序、后序遍历结果重建二叉树
	 * @author rico
	 * @created 2017年5月24日 下午12:24:43
	 * @return
	 */
	public TreeNode createBinaryTreeByInAndPost(String in, String post) {
		if (post.length() > 0) {
			TreeNode root = new TreeNode(post.charAt(post.length() - 1) - 48);
			int index = in.indexOf(post.charAt(post.length() - 1));

			root.left = createBinaryTreeByInAndPost(in.substring(0, index),
					post.substring(0, index));
			root.right = createBinaryTreeByInAndPost(
					in.substring(index + 1, in.length()),
					post.substring(index, post.length() - 1));
			return root;
		}
		return null;
	}

	/**
	 * @description 根据原树的根结点复制出一颗一模一样的树
	 * @author rico
	 * @created 2017年5月23日 下午2:21:08
	 * @param root
	 * @return
	 */
	public TreeNode copy(TreeNode root) {
		if (root == null)
			return null;
		TreeNode TreeNode = new TreeNode();
		TreeNode.val = root.val;
		TreeNode.left = copy(root.left);
		TreeNode.right = copy(root.right);
		return TreeNode;
	}

	/** 方法createTreeByPreOrederStr需要用到的指针 (@author: rico) */
	private int index = 0;

	/**
	 * @description 根据前序遍历结果重建二叉树，所有的叶子节点都用"#"表示
	 * @author rico
	 * @created 2017年5月24日 上午7:51:54
	 * @param preOrderStr
	 * @param temp
	 * @return
	 */
	public TreeNode createTreeByPreOrederStr(char[] preOrderStr, TreeNode temp) {
		if (index < preOrderStr.length) {
			char c = preOrderStr[index++];
			if (c != '#') { // 递归终止条件
				TreeNode TreeNode = new TreeNode(c - 48);
				TreeNode.left = createTreeByPreOrederStr(preOrderStr, TreeNode); // 递归为当前节点创建左子树
				TreeNode.right = createTreeByPreOrederStr(preOrderStr, TreeNode); // 递归为当前节点创建右子树
				return TreeNode;
			}
			return null;
		}
		return null;
	}

	/**
	 * @description
	 * @author rico
	 * @created 2017年6月15日 上午11:40:58
	 * @param root
	 */
	public void Mirror(TreeNode root) {
		if (root == null) { // 递归终止条件
			return; // 简单情景的处理
		} else {
			// 先对调左右子树
			TreeNode tmp = root.left;
			root.left = root.right;
			root.right = tmp;

			// 再将左右子树分别变换为其对应的镜像
			Mirror(root.left); // 重复逻辑的提取，缩小问题规模
			Mirror(root.right); // 重复逻辑的提取，缩小问题规模
		}
	}
	
	public void Mirror1(TreeNode root) {
		LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
		while (root != null || !stack.isEmpty()) {
			if (root != null) {
				// 先对调左右子树
				TreeNode tmp = root.left;
				root.left = root.right;
				root.right = tmp;
				if (root.right != null) {
					stack.push(root.right);
				}
				root = root.left;
			}else{
				root = stack.pop();
			}
		}
	}

	/**
	 * @description 获取树的根结点
	 * @author rico
	 * @created 2017年5月22日 下午3:09:18
	 * @return
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * @description 获得当前结点的左孩子结点
	 * @author rico
	 * @created 2017年5月23日 上午11:13:48
	 * @param TreeNode
	 * @return
	 */
	public TreeNode getLeftChild(TreeNode TreeNode) {
		return TreeNode.left;
	}

	/**
	 * @description 获得当前结点的右孩子结点
	 * @author rico
	 * @created 2017年5月23日 上午11:13:50
	 * @param TreeNode
	 * @return
	 */
	public TreeNode getRightChild(TreeNode TreeNode) {
		return TreeNode.right;
	}

	/**
	 * @description 后序遍历的思想：树中节点个数
	 * @author rico
	 * @created 2017年5月23日 上午11:59:19
	 * @param root
	 * @return
	 */
	public int size(TreeNode root) {
		if (root != null) { // 递归终止条件
			return size(root.left) + size(root.right) + 1;
		}
		return 0;
	}

	/**
	 * @description 后序遍历的思想：树的高度(空树为0)
	 * @author rico
	 * @created 2017年5月23日 下午12:00:08
	 * @param root
	 * @return
	 */
	public int height(TreeNode root) {
		if (root != null) { // 递归终止条件
			int h1 = height(root.left);
			int h2 = height(root.right);
			return h1 > h2 ? h1 + 1 : h2 + 1;
		}
		return 0;
	}

	/**
	 * @description 【有返回值】以广义表的形式打印二叉树：前序遍历的应用
	 * @author rico
	 * @created 2017年5月24日 上午8:13:08
	 * @param root
	 * @return
	 */
	public static String getBinaryTree(TreeNode root) {
		StringBuilder sb = new StringBuilder();
		if (root != null) {
			sb.append(root.val);
			if (root.left != null || root.right != null) {
				sb.append('(');
				sb.append(getBinaryTree(root.left));
				sb.append(',');
				sb.append(getBinaryTree(root.right));
				sb.append(')');
			}
		}
		return sb.toString();
	}

	/**
	 * @description 【无返回值】以广义表的形式打印二叉树：前序遍历的应用
	 * @author rico
	 * @created 2017年5月24日 上午8:13:08
	 * @param root
	 * @return
	 */
	public static void printBinaryTree(TreeNode root) {
		if (root != null) {
			System.out.print(root.val);
			if (root.left != null || root.right != null) {
				System.out.print('(');
				printBinaryTree(root.left);
				System.out.print(',');
				printBinaryTree(root.right);
				System.out.print(')');
			}
		}
	}
	  
	/**     
	 * @description 二叉搜索树转换为排序后的双向链表
	 * @author rico       
	 * @created 2017年6月18日 上午11:39:59     
	 * @param pRootOfTree
	 * @return     
	 */
	public TreeNode Convert(TreeNode pRootOfTree) {
		if (pRootOfTree == null) {
			return null;
		}else{
			TreeNode list1 = Convert(pRootOfTree.left);  // 左子树转换成双向链表1
			TreeNode list2 = Convert(pRootOfTree.right); // 右子树转换成双向链表2
			
			if (list1 == null && list2 == null) {  // 叶节点
				pRootOfTree.left = pRootOfTree;
				pRootOfTree.right = pRootOfTree;
				return pRootOfTree;
			}else if (list1 == null && list2 != null) {  // 左孩子为空
				TreeNode tmp = list2.left;  // 链表的尾节点
				pRootOfTree.right = list2;
				list2.left = pRootOfTree;
				
				pRootOfTree.left = tmp;
				tmp.right = pRootOfTree;
				
				return pRootOfTree;
			}else if (list1 != null && list2 == null){  // 右孩子为空
				TreeNode tmp = list1.left;  // 链表的尾节点
				tmp.right = pRootOfTree;
				pRootOfTree.left = tmp;
				
				list1.left = pRootOfTree;
				pRootOfTree.right = list1;
				
				return list1;
			}else{          // 左、右孩子
				// 双向链表1的尾节点与根结点相连
				TreeNode tmp1 = list1.left;  // 双向链表1的尾结点			
				tmp1.right = pRootOfTree;
				pRootOfTree.left = tmp1;
				
				// 双向链表1的头结点与双向链表2的尾节点相连
				TreeNode tmp2 = list2.left;  // 双向链表2的尾结点
				list1.left = tmp2;
				tmp2.right = list1;
				
				// 根结点与双向链表2的头节点相连
				pRootOfTree.right = list2;
				list2.left = pRootOfTree;
				return list1;
			}
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return levelOrder();
	}

	/**
	 * @description 根据树的根结点判断两颗树是否相等
	 * @author rico
	 * @created 2017年5月23日 下午3:05:35
	 * @param src
	 *            原树的根结点
	 * @param des
	 *            目标树的根结点
	 * @return
	 */
	private boolean equals0(TreeNode src, TreeNode des) {
		if (src == null && des == null) { // 空树相等
			return true;
		} else if (src == null || des == null) { // 空树与非空树不相等
			return false;
		} else { // 非空树与非空树是否相等：当前节点是否相等 && 左子树是否相等 && 右子树是否相等
			return src.equals(des) && equals0(src.left, des.left)
					&& equals0(src.right, des.right);
		}
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof BinaryTree) { // 对方是否也是一颗二叉树
			BinaryTree tree = (BinaryTree) obj;
			return equals0(this.root, tree.root);
		}
		return false;
	}
}