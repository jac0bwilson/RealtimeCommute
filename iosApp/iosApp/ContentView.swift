import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let uiController = UINavigationController( rootViewController: MainViewControllerKt.MainViewController())
        uiController.interactivePopGestureRecognizer?.isEnabled = true
        uiController.isNavigationBarHidden = true
        return uiController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(edges: .bottom)
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



