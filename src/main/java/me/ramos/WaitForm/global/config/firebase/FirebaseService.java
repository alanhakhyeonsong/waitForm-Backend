package me.ramos.WaitForm.global.config.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import me.ramos.WaitForm.domain.board.entity.Board;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    public String insertMember(Member member) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture =
                firestore.collection("MEMBER").document(String.valueOf(member.getId())).set(member);
        return apiFuture.get().getUpdateTime().toString();
    }

    public String insertBoard(Board board) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture =
                firestore.collection("BOARD").document(String.valueOf(board.getId())).set(board);
        return apiFuture.get().getUpdateTime().toString();
    }
}
