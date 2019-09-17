import numpy as np
def matrix_factorization(R,K,all_mean, steps=5000, alpha=0.0002, beta=0.02):
    """
    对评分矩阵进行矩阵分解，同时加入了用户偏好、物品偏好以及L2正则项
    损失函数为：min{∑[(r(u,i) - all_mean - b(u) - b(i) - p(u)q(i))^2 + lamda*(||p(u)||^2 + ||q(i)||^2 + (b(u))^2 + (b(i))^2)]}
    输入:
        R        : 输入矩阵  N x M, 格式为numpy.array
        K        : 隐特征维度
        all_mean : 所有评价分的平均数
        steps    : 最大迭代步数
        alpha    : 学习速率
        beta     : 正则项系数
    输出:
        P        : 分解矩阵P N x K, 格式为numpy.array
        Q        : 分解矩阵Q K x M, 格式为numpy.array
        b_user   : 用户偏好 N x 1 (用户对每个物品的偏好), 格式为numpy.array
        b_item   : 物品偏好 1 x M (可以理解为每个物品的流行程度), 格式为numpy.array
    预测评分矩阵为：T = numpy.dot(P,Q) + b_user + b_item + all_mean
    """
    N = R.shape[0]
    M = R.shape[1]
    P = np.random.rand(N,K)
    Q = np.random.rand(K,M)
    b_user = np.random.rand(N,1)
    b_item = np.random.rand(1,M)
    for step in range(steps):
        for i in range(len(R)):
            for j in range(len(R[i])):
                if R[i][j] > 0:
                    # 计算损失函数
                    eij = R[i][j] - np.dot(P[i,:],Q[:,j]) - b_user[i][0] - b_item[0][j] -all_mean
                    # 对每个参数进行梯度下降
                    b_user[i][0] =  b_user[i][0] + alpha * (2 * eij  - beta * b_user[i][0])
                    b_item[0][j] =  b_item[0][j] + alpha * (2 * eij  - beta *b_item[0][j])
                    for k in range(K):
                        P[i][k] = P[i][k] + alpha * (2 * eij * Q[k][j] - beta * P[i][k])
                        Q[k][j] = Q[k][j] + alpha * (2 * eij * P[i][k] - beta * Q[k][j])
        eR = np.dot(P,Q)
        e = 0
        # 计算误差
        for i in range(len(R)):
            for j in range(len(R[i])):
                if R[i][j] > 0:
                    e = e + pow(R[i][j] - np.dot(P[i,:],Q[:,j] - b_user[i][0] - b_item[0][j] -all_mean), 2)
                    for k in range(K):
                        e = e + (beta/2) * ( pow(P[i][k],2) + pow(Q[k][j],2) + pow(b_item[0][j],2) + pow(b_user[i][0],2))
        if e < 0.001:
            break
    return P, Q,b_user,b_item


if __name__ == "__main__":
    R = [
         [5,4,4,3,5,0],
         [0,4,5,0,3,1],
         [5,4,0,1,3,0],
         [0,4,5,3,1,5],
         [1,0,3,5,0,5],
        ]

    R = np.array(R)

    N = len(R)
    M = len(R[0])
    K = 3

    all_mean = np.mean(R)
    nP, nQ,nb_user,nb_item = matrix_factorization(R,K,all_mean)
    print(R)
    T = np.dot(nP,nQ) + nb_user + nb_item + all_mean
    print(T)

