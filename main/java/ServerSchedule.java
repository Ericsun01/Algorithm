import java.util.Comparator;
import java.util.PriorityQueue;

public class ServerSchedule {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;
        PriorityQueue<Server> idles = new PriorityQueue<>(new Comparator<Server>(){
            @Override
            public int compare(Server s1, Server s2) {
                if (s1.weight == s2.weight) {
                    return s1.id - s2.id;
                }
                return s1.weight - s2.weight;
            }
        });

        PriorityQueue<Task> record = new PriorityQueue<>(new Comparator<Task>(){
            @Override
            public int compare(Task t1, Task t2) {
                if (t1.end == t2.end) {
                    if (t1.s.weight == t2.s.weight) {
                        return t1.s.id - t2.s.id;
                    }
                    return t1.s.weight - t2.s.weight;
                }
                return t1.end-t2.end;
            }
        });

        int[] ans = new int[m];
        int[][] times = process(tasks);

        for (int j=0; j<n; j++) {
            Server s = new Server(j, servers[j]);
            idles.offer(s);
        }

        for (int i=0; i<m; i++) {
            while (!record.isEmpty() && record.peek().end <= times[i][0]) {
                Server newS = record.poll().s;
                idles.offer(newS);
            }

            Server server;
            Task task;
            if (idles.isEmpty()) {
                int timeDiff = record.peek().end-times[i][0];
                server = record.poll().s;
                task = new Task(server, timeDiff+times[i][1]);
            } else {
                server = idles.poll();
                task = new Task(server, times[i][1]);
            }
            record.offer(task);
            ans[i] = server.id;
        }
        return ans;
    }

    private int[][] process(int[] tasks) {
        int[][] ans = new int[tasks.length][2];
        for (int i=0; i<tasks.length; i++) {
            ans[i][0] = i;
            ans[i][1] = i+tasks[i];
        }
        return ans;
    }

    public static void main(String[] args) {
        ServerSchedule ss = new ServerSchedule();
        int[] servers = {31,96,73,90,15,11,1,90,72,9,30,88};
        int[] tasks = {87,10,3,5,76,74,38,64,16,64,93,95,60,79,54,26,30,44,64,71};
        for (int i: ss.assignTasks(servers, tasks)) {
            System.out.print(i);
            System.out.print(" ");
        }
    }
}

class Server {
    int id;
    int weight;

    public Server(int id, int weight) {
        this.id = id;
        this.weight = weight;
    }
}

class Task {
    Server s;
    int end;

    public Task(Server s, int end) {
        this.s = s;
        this.end = end;
    }
}

